package controllers;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.besammen.R;
import com.example.besammen.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import data.CompositeAdapter;
import data.Message;
import data.ReceiverAdapter;
import data.SenderAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    String diagnose;
    String thisUsername;
    String firebaseUsername;
    String date;


    private ArrayList<Message> itemList;
    private ArrayList<Message> itemList2;
    Message message;
    ReceiverAdapter receiverAdapter;
    SenderAdapter senderAdapter;
    CompositeAdapter compositeAdapter;

    boolean getMessagesComplete = false;
    boolean getMyMessagesComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        itemList = new ArrayList<>();
        itemList2 = new ArrayList<>();
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // senderAdapter = new SenderAdapter(ChatActivity.this, R.layout.item_container_sent_message, R.id.textMessage, itemList2);
       // compositeAdapter = new CompositeAdapter(ChatActivity.this, R.layout.item_container_recieved_message, R.id.texmessage_id, itemList, R.layout.item_container_sent_message, R.id.textMessage, itemList2);




        SharedPreferences diagnoseCache = getSharedPreferences("CachedDiagnose", MODE_PRIVATE);
        SharedPreferences savedUsername = getSharedPreferences("CachedUsername", MODE_PRIVATE);

        thisUsername = savedUsername.getString("username", "");

        diagnose = diagnoseCache.getString("diagnoseCache", "");
        binding.diagnose.setText(diagnose);
        //receiverAdapter = new ReceiverAdapter(ChatActivity.this, R.layout.item_container_recieved_message, R.id.texmessage_id, itemList, thisUsername);
        //compositeAdapter = new CompositeAdapter(ChatActivity.this, R.layout.item_container_recieved_message, R.id.texmessage_id, itemList, R.layout.item_container_sent_message, R.id.textMessage, thisUsername);
        //binding.containerMessages.setAdapter(compositeAdapter);

        //getMessages();
        //getMyMessages();
        getMessagesTest();



        FrameLayout testbtn = findViewById(R.id.layoutSend);

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform actions when the ImageView is clicked
                // For example, show a toast message
                sendMessage();
                Toast.makeText(ChatActivity.this, "VIRKER", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getMessagesTest() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(diagnose.toLowerCase())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e(TAG, "Error listening for messages", error);
                            return;
                        }

                        if (value != null) {
                            itemList.clear();

                            for (QueryDocumentSnapshot document : value) {
                                try {
                                    JSONObject jsonMessage = new JSONObject(document.getData());
                                    firebaseUsername = jsonMessage.getString("username");
                                    String date = document.getString("date");
                                    String message = jsonMessage.getString("message");
                                    message = message.replaceAll("[\\n\\r]", ""); // Remove line breaks
                                    Message messageObj = new Message(firebaseUsername, date, message);
                                    itemList.add(messageObj);

                                } catch (JSONException e) {
                                    Log.e(TAG, "Error parsing JSON", e);
                                }
                            }

                            if (compositeAdapter == null) {
                                compositeAdapter = new CompositeAdapter(ChatActivity.this, R.layout.item_container_sent_message, R.id.textMessage, R.layout.item_container_recieved_message, R.id.texmessage_id, itemList, thisUsername);
                                binding.containerMessages.setAdapter(compositeAdapter);
                            } else {
                                compositeAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }





    private void sendMessage() {

        //Element selectors
        EditText inputFelt = findViewById(R.id.inputMessage);

        String inputText = inputFelt.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //Date
        Date currentdate = new Date();

        //Unique ID
        String uniqueID = UUID.randomUUID().toString();


        //skal laves til korrekte username
        //lav variabel der henter fra firebase on create ??
        Message newMessage = new Message(thisUsername, currentdate.toString(), inputText);

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
}


