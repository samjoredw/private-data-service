package com.main.pdsprototype;

import android.annotation.SuppressLint;

/**
 * Simplified GetLocation class.
 * Provides static placeholder coordinates for testing purposes.
 */
public class GetLocation {

    private double latitude;
    private double longitude;
    private float accuracy;

    /**
     * Sets static coordinates and calls getCoordinates() to retrieve them.
     *
     * @return A JSON string with latitude, longitude, and accuracy.
     */
    public String getLocation() {
        // Set local variables
        this.latitude = 37.7749;
        this.longitude = -122.4194;
        this.accuracy = 5.0f;

        // Return coordinates as a JSON string
        return getCoordinates();
    }

    /**
     * Returns the coordinates as a JSON string.
     *
     * @return JSON string with latitude, longitude, and accuracy.
     */
    @SuppressLint("DefaultLocale")
    private String getCoordinates() {
        return String.format(
                "{\"latitude\": %.6f, \"longitude\": %.6f, \"accuracy\": %.2f}",
                latitude, longitude, accuracy
        );
    }
}
