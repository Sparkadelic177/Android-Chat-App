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

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity {

    EditText email;
    EditText username;
    EditText password;
    Button signup;
    ImageView signUpImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.etEmail);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        signup = findViewById(R.id.BtnSignup);
        signUpImage = findViewById(R.id.ivSignup);

        signUpImage.setImageResource(R.mipmap.chat_box);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etEmail = email.getText().toString();
                String etUsername = username.getText().toString();
                String etPassword = password.getText().toString();
                signupUser(etEmail, etPassword, etUsername);

            }
        });

    }

    //method to sign up user
    private void signupUser(String etEmail, String etPassword, String etUsername) {
        ParseUser user = new ParseUser();
        user.setEmail(etEmail);
        user.setUsername(etUsername);
        user.setPassword(etPassword);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e("App", "there was something wrong in the signup " + e.getMessage());
                    Toast.makeText(SignUp.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    return;
                }
                //if the sign up pass go to the chat box
                Toast.makeText(SignUp.this, "Success, Welcome", Toast.LENGTH_LONG).show();
                gottoMessages();

            }
        });


    }

    private void gottoMessages() {
        Intent i = new Intent(this, messagingActivity.class);
        startActivity(i);
        finish();
    }
}
