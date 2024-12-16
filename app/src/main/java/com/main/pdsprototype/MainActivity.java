package com.main.pdsprototype;

import android.app.Activity;
import android.os.Bundle;

/**
 * MainActivity serves as the entry point to the app.
 * For now, it does not start any services or perform actions.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
