package com.main.pdsprototype;

import android.app.Application;
import android.util.Log;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App", "Application started");
    }
}