package controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.besammen.R;
import com.example.besammen.databinding.ActivitySelectedGroupBinding;

public class SelectedGroup extends AppCompatActivity {
    ActivitySelectedGroupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_group);
        binding = ActivitySelectedGroupBinding.inflate(getLayoutInflater());
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