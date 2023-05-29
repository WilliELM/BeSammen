package controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.besammen.R;
import java.util.ArrayList;

import adapters.GroupAdapter;
import data.User;
import data.UserToFirebase;
import com.example.besammen.databinding.ActivityUserPageBinding;
import com.google.firebase.auth.FirebaseAuth;


public class UserPage extends AppCompatActivity {
    private ArrayList<UserToFirebase> itemList;
    ActivityUserPageBinding binding;
    Button logOutAndClearCacheBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        itemList = new ArrayList<>();
        binding = ActivityUserPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        GroupAdapter groupAdapter = new GroupAdapter(UserPage.this,R.layout.item_container_groups,R.id.target,itemList);
        binding.groupContainer.setAdapter(groupAdapter);
        binding.getRoot();


        SharedPreferences diagnoseCache = getSharedPreferences("CachedDiagnose", MODE_PRIVATE);
        SharedPreferences savedUsername = getSharedPreferences("CachedUsername", MODE_PRIVATE);
        String username = savedUsername.getString("username","");
        String diagnose = diagnoseCache.getString("diagnoseCache","");
        binding.groupContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //intent til ny activity skal indsættes her!!!!
                Intent intentToChat = new Intent(UserPage.this, ChatActivity.class);
                intentToChat.putExtra("diagnose",diagnose);
                startActivity(intentToChat);
            }
        });


        // skal ændres til at det er en group i stedet for userToFirebase
        // gruppen skal have samme navn som den diagnose man vælger
        //
        UserToFirebase userToFirebase = new UserToFirebase(username,diagnose);
        itemList.add(userToFirebase);

        Button logOutBtn = findViewById(R.id.logOutBtn2);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser1();
            }
        });
    }






    private void logoutUser1(){

        //FJERNER GEMT DATA NÅR MAN LOGGER UD
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        SharedPreferences.Editor editor2 = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor2.clear();
        editor2.apply();
        FirebaseAuth.getInstance().signOut();
        Intent intentToLogIn = new Intent(UserPage.this, logIn.class);
        startActivity(intentToLogIn);
        return;
    }


}