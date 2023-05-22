package controllers;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.besammen.R;


public class frontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Checker om det er første gang man bruger appen, skifter til "false" når vedkommende har logget ind og valgt diagnose.
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean firstTimeLaunch = preferences.getBoolean("firstTimeLaunch", true);
        if(firstTimeLaunch){
            System.out.println("FØRSTE GANGS USER");
            System.out.println("FØRSTE GANGS USER");
            System.out.println("FØRSTE GANGS USER");
            System.out.println("FØRSTE GANGS USER");
            System.out.println("FØRSTE GANGS USER");
        }
        else{
            Intent intent = new Intent(this, logIn.class);
            startActivity(intent);
            finish();
        }

        /*

        Button registrerBtn = findViewById(R.id.registerBtn);
        registrerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUp();
            }
        });*/
    }
    public void goToSignUp(View v){
        Intent intentToSignUp = new Intent(this, signUp.class);
        startActivity(intentToSignUp);
        Log.d(TAG, "goToSignUp: hello");
    }

    public void goToLogIn(View v){
        Intent intentToLogIn = new Intent(this, logIn.class);
        startActivity(intentToLogIn);
    }
}