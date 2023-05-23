package controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.besammen.R;
import java.util.ArrayList;
import data.GroupAdapter;
import data.User;
import data.UserToFirebase;
import com.example.besammen.databinding.ActivityUserPageBinding;
import com.google.firebase.FirebaseApp;


public class UserPage extends AppCompatActivity implements Userlistener {
    private ArrayList<UserToFirebase> itemList;
    ActivityUserPageBinding binding;



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

        Intent intentFromStartPage = getIntent();
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


    }

    @Override
    public void onUserClicked(User user) {

    }
}