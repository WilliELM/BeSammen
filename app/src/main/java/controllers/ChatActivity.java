package controllers;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.besammen.R;
import com.example.besammen.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.UUID;

import data.GroupAdapter;
import data.Message;
import data.ReceiverAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    String diagnose;
    private ArrayList<Message> itemList;
    Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Den går bare over i den William har lavet førhen
        // Vi skal tjekke op på det her, før det kan virke ->>
        itemList = new ArrayList<>();
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ReceiverAdapter receiverAdapter = new ReceiverAdapter(ChatActivity.this,R.layout.item_container_recieved_message,R.id.dateandtime_id,itemList);
        binding.containerMessages.setAdapter(receiverAdapter);
        binding.getRoot();



        SharedPreferences diagnoseCache = getSharedPreferences("CachedDiagnose", MODE_PRIVATE);
        SharedPreferences savedUsername = getSharedPreferences("CachedUsername", MODE_PRIVATE);
        String username = savedUsername.getString("username","");
        String diagnose = diagnoseCache.getString("diagnoseCache","");

        binding.diagnose.setText(diagnose);
        /*Intent intent = this.getIntent();
        if (intent != null){

            diagnose = intent.getStringExtra("diagnose");
            binding.diagnose.setText(diagnose);

            //ImageView imageView = findViewById(R.id.imageOfMovie);
            // Glide.with(imageView.getContext()).load(picture).into(imageView);
        }

        getMessages();

*/
    }


    private void sendMessage(){

        //Element selectors
        EditText inputFelt = findViewById(R.id.inputMessage);

        String inputText = inputFelt.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //User information
        //Username fetch

        //Date
        Date currentdate = new Date();

        //Unique ID
        String uniqueID = UUID.randomUUID().toString();


        //skal laves til korrekte username
        //lav variabel der henter fra firebase on create ??
        Message newMessage = new Message("willi",currentdate.toString(),inputText);

        db.collection(diagnose.toLowerCase()).document(uniqueID)
                .set(newMessage)
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
        inputFelt.setText("");
    }

    private void getMessages(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(diagnose.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());
                                message = new Message(document.getString("username"), document.getString("date"), document.getString("message"));
                                itemList.add(message);

                                System.out.println(itemList);
                                System.out.println(itemList);
                                System.out.println(itemList);
                                System.out.println(itemList);
                                System.out.println(itemList);


                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });


    }

}