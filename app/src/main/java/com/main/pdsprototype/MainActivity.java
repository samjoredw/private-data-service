package com.main.pdsprototype;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Start the PDSPrototype service
        startService(new Intent(this, PDSPrototype.class));
        // Close the activity after starting the service
        finish();
    }
}