package com.example.chatapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;


//this class is used to get data from the backend and send it to the recycler view
// this class needs to be registered to the parseClass in order to be used.
@ParseClassName("Message")
public class messageModel extends ParseObject {
    public static final String USER_ID_KEY = "userId";
    public static final String BODY_KEY = "body";

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getBody() {
        return getString(BODY_KEY);
    }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setBody(String body) {
        put(BODY_KEY, body);
    }

}
