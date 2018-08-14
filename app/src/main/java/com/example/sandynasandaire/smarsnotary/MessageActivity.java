package com.example.sandynasandaire.smarsnotary;

import android.os.Bundle;
import android.service.autofill.SaveCallback;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.ParseException;


public class MessageActivity extends AppCompatActivity {

        static final String TAG = MessageActivity.class.getSimpleName();
        static final String USER_ID_KEY ="userId";
        static final String BODY_KEY = "body";

        EditText etMessage;
        ImageButton btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // user login
        if (ParseUser.getCurrentUser() != null) {
            startWithCurrentUser();
        } else {
            login();
        }
    }

    void startWithCurrentUser(){
        //todo
        setupMessagePosting();
    }

    //setup button event
    //find the text field and button

    void setupMessagePosting() {

        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (ImageButton) findViewById(R.id.btSend);

        //When send button is

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = etMessage.getText().toString();
                ParseObject message = ParseObject.create("Message");
                message.put(USER_ID_KEY, ParseUser.getCurrentUser().getObjectId());
                message.put(BODY_KEY, data);
                message.saveInBackground(new com.parse.SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            Toast.makeText(MessageActivity.this, "Successfully created message on Parse", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e(TAG, "Failed to save message");
                        }
                    }
                });

                etMessage.setText(null);
            }
        });
    }
    //create an anomymous user
    void login(){
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, com.parse.ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Anonymous login failed", e);
                } else {
                    startWithCurrentUser();
                }

            }
        });
    }
}

