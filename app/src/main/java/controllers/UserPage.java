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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class UserPage extends AppCompatActivity implements Userlistener {
    private ArrayList<UserToFirebase> itemList;
    ActivityUserPageBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

      /*  db.collection("brystkræft").document(String.valueOf(501923))
                .set(brystkræft)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

       */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        itemList = new ArrayList<>();
        binding = ActivityUserPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        GroupAdapter groupAdapter = new GroupAdapter(UserPage.this,R.layout.item_container_groups,R.id.target,itemList);
        binding.groupContainer.setAdapter(groupAdapter);
        binding.getRoot();

        Intent intentFromStartPage = getIntent();
        String username = intentFromStartPage.getStringExtra("username");
        String diagnose = intentFromStartPage.getStringExtra("diagnose");

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


    }

    @Override
    public void onUserClicked(User user) {

    }
}