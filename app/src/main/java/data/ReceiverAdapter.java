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
import java.util.ArrayList;

public class ReceiverAdapter extends ArrayAdapter<Message> {
    ArrayList<Message> itemList;

    public ReceiverAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<Message> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
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

            notifyDataSetChanged();
        }
        return super.getView(position, convertView, parent);

    }
}
