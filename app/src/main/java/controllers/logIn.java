package controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besammen.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logIn extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            return;
        }

        Button logInBtn = findViewById(R.id.logInBtn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });

        TextView switchToRegister = findViewById(R.id.redirectToRegistration);
        switchToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToRegister();
            }
        });
    }

    private void authenticateUser(){
        EditText etLoginUsernameOrEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);

        String usernameOrEmail = etLoginUsernameOrEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (usernameOrEmail.isEmpty() || password.isEmpty()){
            Toast.makeText(logIn.this, "For at komme videre, skal du fylde alle felterne ud", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(usernameOrEmail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            redirectToStartPage();
                        } else {
                            Toast.makeText(logIn.this, "Authentication failed", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void redirectToRegister(){
        Intent intentToRegister = new Intent(this, signUp.class);
        startActivity(intentToRegister);
        finish();
    }

    private void redirectToStartPage(){
        Intent intentToStartPage = new Intent(this,startPage.class);
        startActivity(intentToStartPage);
        finish();
    }
}