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
        return 2; // Hvor mange viewtypes har vi, 1 for sender og 1 for receiver
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getItem(position); //Hent positionen af den givne besked
        if (message.getUsername().equals(thisUsername)) { //hvis beskeden er sendt af brugeren af appen, returner 0 for viewtype Sent messages.
            return 0;
        } else { //Ellers returner 1 for viewtype receiver messages
            return 1;
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
            LayoutInflater inflater = LayoutInflater.from(getContext()); // hvis convertview er nul, så inflate layoutet.
            if (viewType == 0) {
                convertView = inflater.inflate(layoutItemSent, parent, false); //Hvis getItemViewtype returnerer 0 inflate layout for sender
            } else {
                convertView = inflater.inflate(layoutItemReceived, parent, false);//Hvis getItemViewtype returnere 1 inflate layout for receiver
                TextView viewUsername = convertView.findViewById(R.id.username_id); //For receiver skal vi også have username med, så vi kan se hvem der skriver
                viewUsername.setText(message.getUsername());

            }
        }



        TextView viewMessage = convertView.findViewById(viewType == 0 ? textViewResourceIdSent : textViewResourceIdReceived);//Hvis viewtype = 0, brug textview Sent, ellers brug textview received
        TextView viewDate = convertView.findViewById(viewType == 0 ? R.id.textDateTime : R.id.dateandtime_id); //Samme som ovenstående, bare for dato af beskeden der bliver sendt eller modtaget
        //TextView viewUsername = convertView.findViewById(viewType == 1 ? R.id.username_id : R.id.username_id);

        viewMessage.setText(message.getMessage());
        //System.out.println(viewDate);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss - dd-MM-yyyy", Locale.getDefault()); //Nyt format så vores dato er læsbar

        String formattedDate = sdf.format(message.getDate());
        viewDate.setText(formattedDate);



        return convertView;
    }
}

