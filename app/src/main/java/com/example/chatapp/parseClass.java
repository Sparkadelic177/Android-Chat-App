package com.example.chatapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class parseClass extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //importing the class to use as a model class for the recycler view
        ParseObject.registerSubclass(messageModel.class);

        // Use for monitoring Parse network traffic
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("ChatApp") // should correspond to APP_ID env variable
                .clientKey("SparkysChatAPp")  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("https://android-chat-app.herokuapp.com/parse").build());
    }
}
