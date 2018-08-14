package com.example.sandynasandaire.smarsnotary;

import android.app.Application;

import com.parse.Parse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Sandyna Sandaire on 12/08/2018.
 */

public class ChatApplication extends Application {
    @Override

    public void onCreate(){
        super.onCreate();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("SimpleChat-client")
                .clientBuilder(builder)
                .server("https://myparseapp.herokuapp.com/parse/")
                .build());
    }
}

