package controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private String username;
    private String autologinEmail;
    private String autologinPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Intent userDataIntent = getIntent();
        String userName = userDataIntent.getStringExtra("userName");
        username = userName;
        String eMail = userDataIntent.getStringExtra("email");
        autologinEmail = eMail;
        String passWord = userDataIntent.getStringExtra("password");
        autologinPassword = passWord;

        checkBox();

/*
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            return;
        }



 */
        mAuth =FirebaseAuth.getInstance();

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


    //CHECKBOX TIL AUTOLOGIN
    private void checkBox() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        String checker = sharedPreferences.getString("name", "");
        if(checker.equals("user")){
            //ÆNDRE HER FOR AT SKIFTE TIL ANDET INTENT ---------------------------------------------
            Intent intentToSelectedGroup = new Intent(this, UserPage.class);
            intentToSelectedGroup.putExtra("username", username);
            Toast.makeText(this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
            startActivity(intentToSelectedGroup);
            finish();
        }
    }

    private void authenticateUser(){
        EditText etLoginUsernameOrEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.editTextPassword);

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

                            // Autologin data cache, ligenu tjekker den kun efter "true" og ikke dine creds.
                            SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", "user");
                            editor.apply();

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
       /*
        finish();

        */
    }

    private void redirectToStartPage(){
        Intent intentToStartPage = new Intent(this,startPage.class);

        intentToStartPage.putExtra("userName", username);
        intentToStartPage.putExtra("passWord", autologinPassword);
        intentToStartPage.putExtra("eMail", autologinEmail);
        startActivity(intentToStartPage);

        /*
        finish();

         */
    }
}