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

public class GroupAdapter extends ArrayAdapter<UserToFirebase> {
    private ArrayList<UserToFirebase> itemList;


    public GroupAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<UserToFirebase> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // skal erstattes med group
        UserToFirebase userFirebase = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_container_groups,parent,false);
            //select forskellige views
            ImageView groupPic =  convertView.findViewById(R.id.pictures);
            TextView diagnose = convertView.findViewById(R.id.diagnose);
            TextView titel = convertView.findViewById(R.id.titelOfGroup);

            //set views til brugerens valg
            diagnose.setText("Støttegruppe for " + userFirebase.getDiagnose().toLowerCase());
            titel.setText(userFirebase.getDiagnose());

        }
        return super.getView(position, convertView, parent);

    }
}
