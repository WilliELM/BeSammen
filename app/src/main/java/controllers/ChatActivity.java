package controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.besammen.R;
import com.example.besammen.databinding.ActivityChatBinding;
import com.example.besammen.databinding.ActivitySelectedGroupBinding;

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

        Intent intent = this.getIntent();
        if (intent != null){

            String diagnose = intent.getStringExtra("diagnose");
            binding.diagnose.setText(diagnose);

            //ImageView imageView = findViewById(R.id.imageOfMovie);
            // Glide.with(imageView.getContext()).load(picture).into(imageView);


        }
    }
}