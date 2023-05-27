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
    private String autologinEmail;
    private String autologinPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        Button logOutBtn2 = findViewById(R.id.logOutBtn);
        logOutBtn2.setBackgroundColor(getResources().getColor(android.R.color.white));
        Button submitDataBtn = findViewById(R.id.submitDataBtn);
        submitDataBtn.setBackgroundColor(getResources().getColor(android.R.color.white));

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        Intent userDataIntent = getIntent();
        String userName = userDataIntent.getStringExtra("userName");
        username = userName;
        String eMail = userDataIntent.getStringExtra("email");
        autologinEmail = eMail;
        String passWord = userDataIntent.getStringExtra("password");
        autologinPassword = passWord;


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

        //userToFirebase.setDiagnose(spinnerDiagnose.toString());
        System.out.println(diagnose);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");
        Button submitData = findViewById(R.id.submitDataBtn);
        Intent intentToUserPage = new Intent(this, UserPage.class);

        submitData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userToFirebase = new UserToFirebase(username,diagnose);
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("firstTimeLaunch", false);
                editor.apply();
                SharedPreferences diagnoseCache = getSharedPreferences("CachedDiagnose", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = diagnoseCache.edit();
                editor2.putString("diagnoseCache",diagnose);
                editor2.apply();
                // HENT BRUGERNAVN OG DIAGNOSE
                SharedPreferences savedUsername = getSharedPreferences("CachedUsername", MODE_PRIVATE);
                String userTest = savedUsername.getString("username","");
                String diagnose = diagnoseCache.getString("diagnoseCache","");

                db.push().setValue(userToFirebase);
                System.out.println(username);
                System.out.println(userToFirebase.getDiagnose().toString());
                intentToUserPage.putExtra("diagnose",diagnose);
                intentToUserPage.putExtra("username", userTest);
                startActivity(intentToUserPage);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void logoutUser(){

        //FJERNER GEMT DATA NÅR MAN LOGGER UD
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        SharedPreferences.Editor editor2 = sharedPreferences.edit();

        editor.clear();
        editor.apply();
        editor2.clear();
        editor2.apply();

        FirebaseAuth.getInstance().signOut();


        Intent intentToLogIn = new Intent(startPage.this, logIn.class);
        startActivity(intentToLogIn);
        return;
    }
}