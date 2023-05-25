package data;

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

public class CompositeAdapter extends ArrayAdapter<Message> {
    private ArrayList<Message> itemList1;
    private ArrayList<Message> itemList2;
    private int layoutItem1;
    private int layoutItem2;
    private int textViewResourceId1;
    private int textViewResourceId2;

    public CompositeAdapter(@NonNull Context context, int layoutItem1, int textViewResourceId1, @NonNull ArrayList<Message> itemList1, int layoutItem2, int textViewResourceId2, @NonNull ArrayList<Message> itemList2) {
        super(context, layoutItem1, textViewResourceId1, new ArrayList<>(itemList1.size() + itemList2.size()));
        this.itemList1 = itemList1;
        this.itemList2 = itemList2;
        this.layoutItem1 = layoutItem1;
        this.layoutItem2 = layoutItem2;
        this.textViewResourceId1 = textViewResourceId1;
        this.textViewResourceId2 = textViewResourceId2;
        addAll(itemList1);
        addAll(itemList2);
    }

    @Override
    public int getViewTypeCount() {
        return 2; // Number of different view types (for itemList1 and itemList2)
    }

    @Override
    public int getItemViewType(int position) {
        if (position < itemList1.size()) {
            return 0; // View type for itemList1
        } else {
            return 1; // View type for itemList2
        }
    }

    @Override
    public int getCount() {
        return itemList1.size() + itemList2.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int viewType = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if (viewType == 0) {
                convertView = inflater.inflate(layoutItem1, parent, false);
            } else {
                convertView = inflater.inflate(layoutItem2, parent, false);
            }
        }

        Message message;
        if (viewType == 0) {
            message = itemList1.get(position);
        } else {
            message = itemList2.get(position - itemList1.size());
        }

        TextView viewDate = convertView.findViewById(textViewResourceId1);
        TextView viewMessage = convertView.findViewById(textViewResourceId2);

        viewDate.setText(formatDate(message.getDate()));
        viewMessage.setText(message.getMessage());

        return convertView;
    }
    public void updateData() {
        notifyDataSetChanged();
    }


    private String formatDate(String date) {
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
