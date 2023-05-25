package data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.besammen.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReceiverAdapter extends ArrayAdapter<Message> {
    ArrayList<Message> itemList;


    public ReceiverAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<Message> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /*@NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // skal erstattes med group
        Message message = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_container_recieved_message,parent,false);
            //select forskellige views
            //ImageView groupPic =  convertView.findViewById(R.id.pictures);
            TextView ViewMessage = convertView.findViewById(R.id.texmessage_id);
            TextView ViewDate = convertView.findViewById(R.id.dateandtime_id);


            //set views til brugerens valg
            ViewMessage.setText(message.getMessage());
            ViewDate.setText(message.getDate());

            //notifyDataSetChanged();
        }
        return super.getView(position, convertView, parent);

    }*/
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_container_recieved_message, parent, false);
        }

        TextView viewUsername = convertView.findViewById(R.id.username_id);
        TextView viewDate = convertView.findViewById(R.id.dateandtime_id);
        TextView viewMessage = convertView.findViewById(R.id.texmessage_id);

        // Set the values of the keys in the Message object
        //viewUsername.setText(message.getUsername());
        viewDate.setText(formatDate(message.getDate()));
        viewMessage.setText(message.getMessage());

        return convertView;
    }
    private String formatDate (String date){
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date parsedDate = inputFormat.parse(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss", Locale.US);
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date; // Return the original date if there is an error
        }
    }
}
