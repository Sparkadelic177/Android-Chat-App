package com.example.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
    Button signup;
    ImageView chatboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.BtnLogin);
        signup = findViewById(R.id.BtnSignup);
        chatboxes = findViewById(R.id.ivHompage);

        chatboxes.setImageResource(R.mipmap.chat_box);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignupPage();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String pass = password.getText().toString();
                login(name, pass);
            }
        });

        //if user did npt sign out the data will persist
        if (ParseUser.getCurrentUser() != null) {
            gotoMessagePage();
        }

    }

    private void login(String name, String pass) {
        ParseUser.logInInBackground(name, pass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e("App", "there was a problem logging in" + e.getMessage());
                    Toast.makeText(MainActivity.this, "Wrong username / password", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("App", "Great we have logged in");
                gotoMessagePage();
            }
        });

    }

    private void gotoSignupPage() {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }


    private void gotoMessagePage() {
        Intent i = new Intent(this, messagingActivity.class);
        startActivity(i);
        finish();
    }
}
