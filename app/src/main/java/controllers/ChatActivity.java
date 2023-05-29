package controllers;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besammen.R;
import com.example.besammen.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import adapters.CompositeAdapter;
import data.Message;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    String diagnose;
    String thisUsername;
    String firebaseUsername;
    Timestamp currentTimestamp;
    Long numericTimestamp;
    Date date;
    String numericTimestampToString;


    private ArrayList<Message> itemList;
    Message message;
    CompositeAdapter compositeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        itemList = new ArrayList<>();
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //Cached brugernavn og diagnose, som bruges til at query databasen for hvilket chatrum vi skal bruge, samt for at vide hvilke layout der skal bruges i chatten.
        SharedPreferences diagnoseCache = getSharedPreferences("CachedDiagnose", MODE_PRIVATE);
        SharedPreferences savedUsername = getSharedPreferences("CachedUsername", MODE_PRIVATE);

        thisUsername = savedUsername.getString("username", "");

        diagnose = diagnoseCache.getString("diagnoseCache", "");
        binding.diagnose.setText(diagnose);
        getMessagesTest();



        FrameLayout testbtn = findViewById(R.id.layoutSend);
        View arrowbtn = findViewById(R.id.imageBack);
        View informationbtn = findViewById(R.id.imageInfo);
        informationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popUpInfo();
                Toast.makeText(ChatActivity.this, "Information success", Toast.LENGTH_SHORT).show();

            }
        });

        Intent intentToUserpage = new Intent(this, UserPage.class);
        arrowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intentToUserpage);
                Toast.makeText(ChatActivity.this, "Returned to userpage", Toast.LENGTH_SHORT).show();

            }
        });

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

    private void popUpInfo () {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popupmessage);
        TextView txt = (TextView)dialog.findViewById(R.id.TextForPopup);
        TextView txtTitel = (TextView)dialog.findViewById(R.id.TitleForPopup);
        txtTitel.setText("Information");
        String textt  = (getString(R.string.beskrivelse_information));

        Spanned formattedBeskrivelse = Html.fromHtml(textt);
        txt.setText(formattedBeskrivelse);
        dialog.show();

        CheckBox checkBox = dialog.findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Toast.makeText(ChatActivity.this, "VIRKER", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void getMessagesTest() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(diagnose.toLowerCase())//Hent chatrummet efter hvilken diagnose brugeren har valgt i appen
                .orderBy("orderingDate", Query.Direction.ASCENDING)//OrderBy datoen sendt
                .addSnapshotListener(new EventListener<QuerySnapshot>() { //snapshotlistener til realtime updates af chatten
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
                                    JSONObject jsonMessage = new JSONObject(document.getData());//Bruger her json til at parse det data vi får fra databasen
                                    firebaseUsername = jsonMessage.getString("username");
                                    date = document.getDate("date");
                                    numericTimestamp = document.getLong("orderingDate");
                                    String message = jsonMessage.getString("message");
                                    message = message.replaceAll("[\\n\\r]", ""); // Fjerner linebreaks og gør beskeden læsbar
                                    Message messageObj = new Message(firebaseUsername, date, numericTimestamp, message);
                                    itemList.add(messageObj);

                                } catch (JSONException e) {
                                    Log.e(TAG, "Error parsing JSON", e);
                                }
                            }


                            if (compositeAdapter == null) { //Hvis vi ikke har kørt adapteren, så skal den køre og initialisere vores binding.containerMessages
                                compositeAdapter = new CompositeAdapter(ChatActivity.this, R.layout.item_container_sent_message, R.id.textMessage, R.layout.item_container_recieved_message, R.id.texmessage_id, itemList, thisUsername);
                                binding.containerMessages.setAdapter(compositeAdapter);
                            } else { //Ellers opdater listen
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
        date = new Date();
        numericTimestamp = date.getTime(); //For at kunne order by date, skal vi have det numeriske timestamp af datoen, fordi ellers vil den ikke order listen korrekt
        numericTimestampToString = numericTimestamp.toString();


        // System.out.println(numericTimestamp);
        // System.out.println(date);




        //Unique ID
        String uniqueID = UUID.randomUUID().toString();



        Message newMessage = new Message(thisUsername, date, numericTimestamp, inputText);

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





