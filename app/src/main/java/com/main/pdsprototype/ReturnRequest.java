package com.main.pdsprototype;

import android.util.Log;

/**
 * ReturnRequest gathers the necessary data and sends a response back to CityOS.
 */
public class ReturnRequest {

    private static final String TAG = "ReturnRequest";

    private final GetLocation getLocation;

    /**
     * Constructor initializes the dependency on GetLocation.
     *
     * @param getLocation The GetLocation instance used to fetch location data.
     */
    public ReturnRequest(GetLocation getLocation) {
        this.getLocation = getLocation;
    }

    /**
     * Returns location data as a JSON string.
     *
     * @return JSON-formatted location data.
     */
    public String returnLocation() {
        Log.d(TAG, "Returning location data...");
        String locationJson = getLocation.getLocation();
        Log.d(TAG, "Location data: " + locationJson);
        return locationJson;
    }

    /**
     * Returns generic data (e.g., status or other placeholder responses).
     *
     * @return JSON-formatted generic data.
     */
    public String returnData() {
        Log.d(TAG, "Returning generic data...");
        String genericJson = "{\"status\": \"success\", \"message\": \"This is a placeholder for general data.\"}";
        Log.d(TAG, "Generic data: " + genericJson);
        return genericJson;
    }
}
