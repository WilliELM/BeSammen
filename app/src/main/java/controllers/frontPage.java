package controllers;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.besammen.R;


public class frontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
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
}