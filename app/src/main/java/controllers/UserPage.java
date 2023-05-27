package controllers;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.besammen.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.GroupAdapter;
import data.User;
import data.UserToFirebase;
import com.example.besammen.databinding.ActivityUserPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class UserPage extends AppCompatActivity implements Userlistener {
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

        Intent intentFromStartPage = getIntent();


       /* db.collection(diagnose)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
*/

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

    @Override
    public void onUserClicked(User user) {

    }
}