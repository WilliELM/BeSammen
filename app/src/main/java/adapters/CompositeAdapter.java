package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class CompositeAdapter extends ArrayAdapter<Message> {
    private int layoutItemSent;
    private int layoutItemReceived;
    private int textViewResourceIdSent;
    private int textViewResourceIdReceived;
    private String thisUsername;
    private String date;

    public CompositeAdapter(@NonNull Context context, int layoutItemSent, int textViewResourceIdSent, int layoutItemReceived, int textViewResourceIdReceived, @NonNull ArrayList<Message> itemList, String thisUsername) {
        super(context, 0, itemList);
        this.layoutItemSent = layoutItemSent;
        this.layoutItemReceived = layoutItemReceived;
        this.textViewResourceIdSent = textViewResourceIdSent;
        this.textViewResourceIdReceived = textViewResourceIdReceived;
        this.thisUsername = thisUsername;
    }

    @Override
    public int getViewTypeCount() {
        return 2; // Number of different view types (for sent and received messages)
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getItem(position);
        if (message.getUsername().equals(thisUsername)) {
            return 0; // View type for sent messages
        } else {
            return 1; // View type for received messages
        }
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int viewType = getItemViewType(position);
        Message message = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if (viewType == 0) {
                convertView = inflater.inflate(layoutItemSent, parent, false);
            } else {
                convertView = inflater.inflate(layoutItemReceived, parent, false);
                TextView viewUsername = convertView.findViewById(R.id.username_id);
                viewUsername.setText(message.getUsername());

            }
        }



        TextView viewMessage = convertView.findViewById(viewType == 0 ? textViewResourceIdSent : textViewResourceIdReceived);
        TextView viewDate = convertView.findViewById(viewType == 0 ? R.id.textDateTime : R.id.dateandtime_id);
        //TextView viewUsername = convertView.findViewById(viewType == 1 ? R.id.username_id : R.id.username_id);

        viewMessage.setText(message.getMessage());
        //System.out.println(viewDate);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss - dd-MM-yyyy", Locale.getDefault());

        String formattedDate = sdf.format(message.getDate());
        viewDate.setText(formattedDate);



        return convertView;
    }
}

