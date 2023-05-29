package adapters;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.besammen.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import data.Message;

public class ReceiverAdapter extends ArrayAdapter<Message> {
    ArrayList<Message> itemList;
    String thisUsername;

    public ReceiverAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<Message> objects, String username) {
        super(context, resource, textViewResourceId, objects);
        itemList = objects;
        thisUsername = username;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Message message = getItem(position);
        if (!thisUsername.toLowerCase().equals(message.getUsername().toLowerCase())){
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_container_recieved_message, parent, false);
        }
        TextView viewUsername = convertView.findViewById(R.id.username_id);
        TextView viewDate = convertView.findViewById(R.id.dateandtime_id);
        TextView viewMessage = convertView.findViewById(R.id.texmessage_id);

        // Set værdier
        viewUsername.setText(message.getUsername());
        //viewDate.setText(formatDate(message.getDate()));
        viewMessage.setText(message.getMessage());

        return convertView;
    }else {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_container_sent_message, parent, false);
            }

            TextView viewUsername = convertView.findViewById(R.id.username_id);
            TextView viewDate = convertView.findViewById(R.id.textDateTime);
            TextView viewMessage = convertView.findViewById(R.id.textMessage);

            // Set værdier
            //viewUsername.setText(message.getUsername());
           // viewDate.setText(message.getDate());
            viewMessage.setText(message.getMessage());

            return convertView;
        }
    }

    private String formatDate (String date){
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date parsedDate = inputFormat.parse(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss", Locale.US);
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }
}
