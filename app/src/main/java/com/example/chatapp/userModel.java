package com.example.chatapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("User")
public class userModel extends ParseObject {
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    public void setUsername (String username){
        put(USER_NAME, username);
    }

    public void setPassword(String password){
        put(PASSWORD, password);
    }

    public void setEmail(String email){
        put(EMAIL,email);
    }




}
