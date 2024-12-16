package com.main.pdsprototype;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PDSService extends Service {
    private static final String TAG = "PDSService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "PDS Service Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "PDS Service Started");
        // Return sticky to ensure the service keeps running
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "PDS Service Destroyed");
    }
}