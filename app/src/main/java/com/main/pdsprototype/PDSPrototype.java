package com.main.pdsprototype;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class PDSPrototype extends Service {

    private LocationManager locationManager;  // manages location updates

    // load the rust library (change privacy_lib to match actual .so library name)
    static {
        System.loadLibrary("privacy_lib"); // Replace with actual Rust library name
    }

    // native method to calculate privacy loss via rust (integrated with JNI)
    public native float calculatePrivacyLoss(float precision);

    @SuppressLint("InlinedApi")
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("PDSPrototype", "Service onCreate() started");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // register broadcast receivers conditionally based on API level
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            registerReceiver(cityOSCommandReceiver, new IntentFilter("com.cityos.STORE_LOCATION"),
                    Context.RECEIVER_NOT_EXPORTED);
            registerReceiver(wifiBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION),
                    Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(cityOSCommandReceiver, new IntentFilter("com.cityos.STORE_LOCATION"),
                    Context.RECEIVER_NOT_EXPORTED);
            registerReceiver(wifiBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        }

        requestLocationUpdates();
    }

    // requests location updates from the GPS provider at regular intervals
    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("PDSPrototype", "Location permission not granted");
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        Log.d("PDSPrototype", "Location updated: " + location.toString());
                        float precision = location.getAccuracy();
                        float privacyLoss = calculatePrivacyLoss(precision);

                        broadcastPrivacyLoss(location, privacyLoss);
                        sendReportToBackend(location, privacyLoss);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {}

                    @Override
                    public void onProviderEnabled(@NonNull String provider) {}

                    @Override
                    public void onProviderDisabled(@NonNull String provider) {}
                }
        );
    }

    // handle commands from CityOS to store location data
    private final BroadcastReceiver cityOSCommandReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String appIdentifier = intent.getStringExtra("appIdentifier");

            Log.d("PDSPrototype", "Received Broadcast: " + action + ", App ID: " + appIdentifier);
            Toast.makeText(context, "Received Broadcast: " + action + ", App ID: " + appIdentifier, Toast.LENGTH_LONG).show();

            if ("com.cityos.STORE_LOCATION".equals(action)) {
                //TODO
                System.out.println();
            }
        }
    };

    // Gets the current location and calculates privacy loss, broadcasting and sending data
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                float privacyLoss = calculatePrivacyLoss(location.getAccuracy());

                broadcastPrivacyLoss(location, privacyLoss);
                sendReportToBackend(location, privacyLoss);
            }
        }
    }

    // for wifi scan results, simulating location updates when a scan completes
    private final BroadcastReceiver wifiBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) {
                Log.d("PDSPrototype", "WiFi scan detected, simulating location update.");
                getCurrentLocation();
            }
        }
    };

    private void broadcastPrivacyLoss(Location location, float privacyLoss) {
        Intent intent = new Intent("com.example.PRIVACY_UPDATE");
        intent.putExtra("latitude", location.getLatitude());
        intent.putExtra("longitude", location.getLongitude());
        intent.putExtra("privacyLoss", privacyLoss);
        sendBroadcast(intent);
    }

    private void sendReportToBackend(Location location, float privacyLoss) {
        try {
            URL url = new URL("ADD BACKEND URL");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject payload = new JSONObject();
            payload.put("latitude", location.getLatitude());
            payload.put("longitude", location.getLongitude());
            payload.put("privacyLoss", privacyLoss);

            OutputStream os = conn.getOutputStream();
            os.write(payload.toString().getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.i("PDSPrototype", "Report sent, response code: " + responseCode);

        } catch (Exception e) {
            Log.e("PDSPrototype", "Failed to send report", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(cityOSCommandReceiver);
        unregisterReceiver(wifiBroadcastReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
