package controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besammen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

import data.User;
import data.UserToFirebase;

public class startPage extends AppCompatActivity {

    TextView tvUsername,tvEmail,tvGodkendtPassword;
    Spinner spinnerDiagnose;
    private FirebaseAuth mAuth;
    UserToFirebase userToFirebase;
    String diagnose;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        Intent usernameIntent = getIntent();
        String userName = usernameIntent.getStringExtra("userName");
        username = userName;


        ///////////// DIAGNOSE VALG /////////////

        spinnerDiagnose = findViewById(R.id.spinnerDiagnose);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.diagnoseArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        adapter.add(getString(R.string.placeholder_diagnose));

        adapter.addAll(getResources().getStringArray(R.array.diagnoseArray));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDiagnose.setAdapter(adapter);

        // Lyt til spinnerens valgte element

        spinnerDiagnose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Få den valgte tekst fra spinneren
                diagnose = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Håndter, hvis intet element er valgt (valgfrit)
                Toast.makeText(startPage.this, "Du skal vælge en diagnose", Toast.LENGTH_SHORT).show();
            }
        });
        userToFirebase = new UserToFirebase();
        //userToFirebase.setDiagnose(spinnerDiagnose.toString());
        System.out.println(diagnose);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");
        Button submitData = findViewById(R.id.submitDataBtn);
        submitData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userToFirebase.setName(username);
                userToFirebase.setDiagnose(diagnose);
                db.push().setValue(userToFirebase);
                System.out.println(username);
                System.out.println(userToFirebase.getDiagnose().toString());
            }
        });





        /*
        if (currentUser == null){
            Intent intentToLogIn = new Intent(startPage.this, logIn.class);
            startActivity(intentToLogIn);
            finish();
            return;
        }

         */

        mAuth = FirebaseAuth.getInstance();

        /*
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvGodkendtPassword = findViewById(R.id.tvGodkendtPassword);
        */

        Button logOutBtn = findViewById(R.id.logOutBtn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null){
                    tvUsername.setText("Username: " + user.username);
                    tvEmail.setText("E-mail: " + user.eMail);
                    tvGodkendtPassword.setText("Godkendt Password: " + user.godkendtPassword);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent intentToLogIn = new Intent(startPage.this, logIn.class);
        startActivity(intentToLogIn);
        return;
    }
}