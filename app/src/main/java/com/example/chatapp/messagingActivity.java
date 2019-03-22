package com.example.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class messagingActivity extends AppCompatActivity {

    EditText etMessage;
    Button btSend;
    Button btnLogout;
    RecyclerView rvChat;
    ArrayList<messageModel> mMessages;
    ChatAdapter mAdapter;
    // Keep track of initial load to scroll to the bottom of the ListView
    boolean mFirstLoad;
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    final String userId = ParseUser.getCurrentUser().getObjectId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        etMessage = findViewById(R.id.etMessage);
        btSend = findViewById(R.id.btSend);
        rvChat = findViewById(R.id.rvChat);
        mMessages = new ArrayList<>();
        mFirstLoad = true;
        mAdapter = new ChatAdapter(this, userId, mMessages);
        rvChat.setAdapter(mAdapter);
        btnLogout = findViewById(R.id.BtnLogout);

        // associate the LayoutManager with the RecylcerView
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true); //starting from the bottom up.
        rvChat.setLayoutManager(linearLayoutManager);
        refreshData();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent i = new Intent(messagingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
//                ParseObject message = ParseObject.create("Message"); //creates a new class on the fly , if doesn't exist
//                message.put(USER_ID_KEY, ParseUser.getCurrentUser().getObjectId()); //makes column that points to current user
//                message.put(BODY_KEY, data); //makes a column with the data that is passed

                //better way to use it when you have the model class
                messageModel message = new messageModel();
                message.setBody(data);
                message.setUserId(ParseUser.getCurrentUser().getObjectId());


                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Toast.makeText(messagingActivity.this, "Successfully created message on Parse",
                                    Toast.LENGTH_SHORT).show();
                            refreshData();
                        } else {
                            Log.e("App", "Failed to save message", e);
                        }
                    }
                });
                etMessage.setText(null); //clears the input box once the data is sent
            }
        });

    }

    private void refreshData() {
        ParseQuery<messageModel> query = ParseQuery.getQuery(messageModel.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);

        // get the latest 50 messages, order will show up newest to oldest of this group
        query.orderByDescending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<messageModel>() {
            public void done(List<messageModel> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        rvChat.scrollToPosition(0);
                        mFirstLoad = false;
                    }
                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });

    }


}

