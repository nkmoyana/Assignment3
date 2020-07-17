package com.example.android.assignment3;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class ApplicationClass extends Application
{
    public static final String APPLICATION_ID = "54992D8A-2555-126A-FF75-1755CF676600";
    public static final String API_KEY = "79E63A62-3F92-445D-AA6B-9C35322B3A98";
    public static final String SERVER_URL = "https://api.backendless.com";

    public static BackendlessUser user;

    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}
