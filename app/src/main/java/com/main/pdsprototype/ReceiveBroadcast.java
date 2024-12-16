package com.main.pdsprototype;

import android.os.Build;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ReceiveBroadcast processes aggregate data broadcasts from API 2.
 * It logs the privacy loss caused by the broadcast and optionally sends updates back.
 */
public class ReceiveBroadcast {

    private static final String TAG = "ReceiveBroadcast";

    private final LogPrivacyLoss logPrivacyLoss;
    private final ReturnPrivacyLog returnPrivacyLog;

    /**
     * Constructor for ReceiveBroadcast.
     *
     * @param logPrivacyLoss A reference to the LogPrivacyLoss class.
     * @param returnPrivacyLog A reference to the ReturnPrivacyLog class.
     */
    public ReceiveBroadcast(LogPrivacyLoss logPrivacyLoss, ReturnPrivacyLog returnPrivacyLog) {
        this.logPrivacyLoss = logPrivacyLoss;
        this.returnPrivacyLog = returnPrivacyLog;
    }

    /**
     * Processes an API 2 broadcast.
     *
     * @param broadcastType The type of broadcast received (e.g., "privacy_beacon").
     * @param metadata Optional metadata associated with the broadcast.
     * @param privacyLoss The privacy loss value included in the broadcast.
     */
    public void processBroadcast(String broadcastType, String metadata, float privacyLoss) {
        Log.d(TAG, "Processing broadcast of type: " + broadcastType);

        String timestamp = getCurrentTimestamp();

        // Handle specific broadcast types
        switch (broadcastType) {
            case "privacy_beacon":
                Log.d(TAG, "Processing privacy beacon broadcast...");

                // Log the privacy loss locally
                logPrivacyLoss.addPrivacyLossLog("Broadcast", timestamp, privacyLoss);

                returnPrivacyLog.sendPrivacyLogsToServer();
                returnPrivacyLog.sendTotalPrivacyLoss();
                break;

            case "aggregate_data":
                Log.d(TAG, "Processing aggregate data broadcast...");
                Log.d(TAG, "Metadata: " + metadata);

                logPrivacyLoss.addPrivacyLossLog("Broadcast", timestamp, privacyLoss);

                break;

            default:
                Log.w(TAG, "Unknown broadcast type received: " + broadcastType);
                break;
        }

        Log.d(TAG, "Privacy loss for this broadcast: " + privacyLoss);
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
