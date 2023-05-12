package controllers;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.besammen.R;

public class frontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

    }

    public void goToSignUp(View view){
        Intent intentToSignUp = new Intent(this, signUp.class);
        startActivity(intentToSignUp);
        Log.d(TAG, "goToSignUp: hello");
    }
}