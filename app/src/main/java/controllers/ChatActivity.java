package controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.besammen.R;
import com.example.besammen.databinding.ActivityChatBinding;
import com.example.besammen.databinding.ActivitySelectedGroupBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Den går bare over i den William har lavet førhen
        // Vi skal tjekke op på det her, før det kan virke ->>
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences diagnoseCache = getSharedPreferences("CachedDiagnose", MODE_PRIVATE);
        SharedPreferences savedUsername = getSharedPreferences("CachedUsername", MODE_PRIVATE);
        String username = savedUsername.getString("username","");
        String diagnose = diagnoseCache.getString("diagnoseCache","");

        binding.diagnose.setText(diagnose);
        /*Intent intent = this.getIntent();
        if (intent != null){

            String diagnose = intent.getStringExtra("diagnose");
            binding.diagnose.setText(diagnose);

            //ImageView imageView = findViewById(R.id.imageOfMovie);
            // Glide.with(imageView.getContext()).load(picture).into(imageView);


        }*/
    }
}