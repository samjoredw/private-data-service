package com.main.pdsprototype;

import android.os.Build;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * ReceiveRequest processes incoming requests from API 3, delegates tasks,
 * and ensures the correct response is generated and sent.
 */
public class ReceiveRequest {

    private static final String TAG = "ReceiveRequest";
    private final CalculatePrivacyLoss calculatePrivacyLoss;
    private final LogPrivacyLoss logPrivacyLoss;
    private final ReturnRequest returnRequest;

    /**
     * Constructor for ReceiveRequest.
     *
     * @param calculatePrivacyLoss A reference to the CalculatePrivacyLoss class.
     * @param logPrivacyLoss       A reference to the LogPrivacyLoss class.
     * @param returnRequest        A reference to the ReturnRequest class.
     */
    public ReceiveRequest(CalculatePrivacyLoss calculatePrivacyLoss, LogPrivacyLoss logPrivacyLoss, ReturnRequest returnRequest) {
        this.calculatePrivacyLoss = calculatePrivacyLoss;
        this.logPrivacyLoss = logPrivacyLoss;
        this.returnRequest = returnRequest;
    }

    /**
     * Processes a request received from API 3.
     *
     * @param requestType The type of request received (e.g., "location_request", "status_request").
     * @param metadata    Optional metadata associated with the request.
     */
    public void processRequest(String requestType, String metadata) {
        Log.d(TAG, "Processing request of type: " + requestType);

        float privacyLoss = 0.0f;
        String timestamp = getCurrentTimestamp();

        switch (requestType) {
            case "location_request":
                Log.d(TAG, "Requesting location information...");

                privacyLoss = calculatePrivacyLoss.computeLoss(5.0f, metadata);

                logPrivacyLoss.addPrivacyLossLog("Request", timestamp, privacyLoss);

                String locationData = returnRequest.returnLocation();
                Log.d(TAG, "Location data returned: " + locationData);
                break;

            case "status_request":
                Log.d(TAG, "Requesting status information...");

                privacyLoss = calculatePrivacyLoss.computeLoss(3.0f, metadata);

                logPrivacyLoss.addPrivacyLossLog("Request", timestamp, privacyLoss);

                String genericData = returnRequest.returnData();
                Log.d(TAG, "Generic data returned: " + genericData);
                break;

            default:
                Log.w(TAG, "Unknown request type received: " + requestType);
                break;
        }

        Log.d(TAG, "Privacy loss for this request: " + privacyLoss);
    }

    /**
     * Gets the current timestamp in a formatted string.
     *
     * @return The current timestamp as a String.
     */
    private String getCurrentTimestamp() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return dateFormat.format(new Date());
        } catch (Exception e) {
            return "Timestamp Error";
        }
    }
}
