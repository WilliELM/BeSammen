package com.example.besammen.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.besammen.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import adapters.CompositeAdapter;
import data.Message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class CompositeAdapterTest {
    private CompositeAdapter adapter;
    private ArrayList<Message> itemList;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        int layoutItemSent = R.layout.item_container_sent_message;
        int textViewResourceIdSent = R.id.textMessage;
        int layoutItemReceived = R.layout.item_container_recieved_message;
        int textViewResourceIdReceived = R.id.texmessage_id;
        String thisUsername = "JohnDoe";


        itemList = new ArrayList<>();
        itemList.add(new Message("JohnDoe", new Date(), 1L, "Hello"));
        itemList.add(new Message("JaneSmith", new Date(), 2L, "Hi there"));

        adapter = new CompositeAdapter(context, layoutItemSent, textViewResourceIdSent, layoutItemReceived, textViewResourceIdReceived, itemList, thisUsername);
    }

    @Test
    public void getViewTypeCount_returnsCorrectViewTypeCount() {
        int viewTypeCount = adapter.getViewTypeCount();
        assertEquals(2, viewTypeCount);
    }

    @Test
    public void getItemViewType_returnsCorrectViewType() {
        int viewTypeSent = adapter.getItemViewType(0);
        int viewTypeReceived = adapter.getItemViewType(1);

        assertEquals(0, viewTypeSent);
        assertEquals(1, viewTypeReceived);
    }

    @Test
    public void getCount_returnsCorrectItemCount() {
        int itemCount = adapter.getCount();
        assertEquals(itemList.size(), itemCount);
    }

    @Test
    public void getView_inflatesCorrectLayoutAndBindsData() {
        View convertView = adapter.getView(0, null, new ViewGroup(ApplicationProvider.getApplicationContext()) {
            @Override
            protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

            }
        });

        assertNotNull(convertView);

        // Test for sent message
        TextView viewMessageSent = convertView.findViewById(R.id.textMessage);
        TextView viewDateSent = convertView.findViewById(R.id.textDateTime);

        assertNotNull(viewMessageSent);
        assertNotNull(viewDateSent);

        assertEquals("Hello", viewMessageSent.getText().toString());
        // You can add more assertions for the date and other views if needed

        // Test for received message
        convertView = adapter.getView(1, null, new ViewGroup(ApplicationProvider.getApplicationContext()) {
            @Override
            protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

            }
        });

        assertNotNull(convertView);

        TextView viewUsernameReceived = convertView.findViewById(R.id.username_id);
        TextView viewMessageReceived = convertView.findViewById(R.id.texmessage_id);
        TextView viewDateReceived = convertView.findViewById(R.id.dateandtime_id);

        assertNotNull(viewUsernameReceived);
        assertNotNull(viewMessageReceived);
        assertNotNull(viewDateReceived);

        assertEquals("JaneSmith", viewUsernameReceived.getText().toString());
        assertEquals("Hi there", viewMessageReceived.getText().toString());
        // You can add more assertions for the date and other views if needed
    }
}
