package controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.besammen.R;
import com.example.besammen.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_chat);
    }
}