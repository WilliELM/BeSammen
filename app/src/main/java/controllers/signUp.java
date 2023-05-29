package controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    // brugt til auth
    private FirebaseAuth mAuth;
    private String userName;
    private String autologinEmail;
    private String autologinPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        // intialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        // Hvis brugeren allerede er authenticated, så lukker man den her aktivitet


        Button registrerBtn = findViewById(R.id.registrerBtn);
        registrerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
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
    private void registerUser() {

        EditText etusername = findViewById(R.id.editTextNavn);
        EditText etEMail = findViewById(R.id.editTextEmail);
        EditText etPassword = findViewById(R.id.editTextPassword);
        EditText etGodkendtPassword = findViewById(R.id.editTextGodkendtPassword);

        String username = etusername.getText().toString();
        String eMail = etEMail.getText().toString();
        String password = etPassword.getText().toString();
        String godkendtPassword = etGodkendtPassword.getText().toString();

        userName = username;
        autologinEmail = eMail;
        autologinPassword = password;


        if (username.isEmpty() || eMail.isEmpty() || password.isEmpty() || godkendtPassword.isEmpty()) {
            Toast.makeText(signUp.this, "For at komme videre skal du udfylde alle felterne", Toast.LENGTH_LONG).show();
            return;
        }


        if (password.length() >= 6 && password.equals(godkendtPassword)) {
                mAuth.createUserWithEmailAndPassword(eMail, godkendtPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(username,eMail,godkendtPassword);
                                    SharedPreferences savedUsername = getSharedPreferences("CachedUsername", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = savedUsername.edit();
                                    editor.putString("username", username);
                                    editor.apply();
                                    FirebaseDatabase.getInstance().getReference("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    redirectToLogIn ();
                                                }
                                            });
                                } else {
                                    Toast.makeText(signUp.this,"Authentication fejlede",Toast.LENGTH_LONG).show();
                                }
                            }

                        });
        } else {
            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "Kodeordet skal være længere end 6 karaktere", Toast.LENGTH_SHORT).show();

            }
            Toast.makeText(getApplicationContext(), "Kodeordene matcher ikke", Toast.LENGTH_SHORT).show();
        }
                etPassword.setText("");
                etGodkendtPassword.setText("");
                return;
            }

    private void redirectToLogIn(){
        Intent intentToLogIn = new Intent(this, logIn.class);
        intentToLogIn.putExtra("userName", userName);
        intentToLogIn.putExtra("email", autologinEmail);
        intentToLogIn.putExtra("password", autologinPassword);
        startActivity(intentToLogIn);
        System.out.println(userName);


    }
}