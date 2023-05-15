package controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.besammen.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import data.User;

public class signUp extends AppCompatActivity {

    // https://www.youtube.com/watch?v=LeIFNsiKmVY&ab_channel=BoostMyTool

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        // intialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        // Hvis brugeren allerede er authenticated, så lukker man den her aktivitet
        /*
        if (mAuth.getCurrentUser() != null ){
            finish();
            return;
        }

         */

        Button registrerBtn = findViewById(R.id.registrerBtn);
        registrerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                redirectToLogIn();
            }
        });

        Button ToLogIn = findViewById(R.id.redirectToLogInBtn);

        ToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToLogIn ();
            }
        });

    }

    // https://firebase.google.com/docs/auth/android/password-auth#java_1
    private void registerUser(){

        EditText etusername = findViewById(R.id.editTextNavn);
        EditText etEMail = findViewById(R.id.editTextEmail);
        EditText etPassword = findViewById(R.id.editTextPassword);
        EditText etGodkendtPassword = findViewById(R.id.editTextGodkendtPassword);

        String username = etusername.getText().toString();
        String eMail = etEMail.getText().toString();
        String password = etPassword.getText().toString();
        String godkendtPassword = etGodkendtPassword.getText().toString();

        if (username.isEmpty() || eMail.isEmpty() || password.isEmpty() || godkendtPassword.isEmpty()){
            Toast.makeText(signUp.this, "For at komme videre skal du udfylde alle felterne", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(eMail, godkendtPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(username,eMail,godkendtPassword);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            /*
                                            redirectToLogIn ();

                                             */
                                        }
                                    });
                        } else {
                            Toast.makeText(signUp.this,"Authentication fejlede",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void redirectToLogIn(){
        Intent intentToLogIn = new Intent(this, logIn.class);
        startActivity(intentToLogIn);
        System.out.println("hello");
    }
}