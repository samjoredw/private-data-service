package com.main.pdsprototype;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * LogPrivacyLoss maintains a local record of privacy loss logs.
 * This includes logs for requests and broadcasts.
 */
public class LogPrivacyLoss {

    /**
     * Represents a single privacy loss log entry.
     */
    public static class PrivacyLossEntry {
        public String source; // The source of the privacy loss (e.g., "Request", "Broadcast")
        public String timestamp; // Timestamp of the log entry
        public float privacyLoss; // Privacy loss incurred

        public PrivacyLossEntry(String source, String timestamp, float privacyLoss) {
            this.source = source;
            this.timestamp = timestamp;
            this.privacyLoss = privacyLoss;
        }

        @NonNull
        @Override
        public String toString() {
            return "Source: " + source + ", Timestamp: " + timestamp + ", Privacy Loss: " + privacyLoss;
        }
    }

    private final List<PrivacyLossEntry> logs;

    /**
     * Constructor initializes the log storage.
     */
    public LogPrivacyLoss() {
        this.logs = new ArrayList<>();
    }

    /**
     * Adds a new privacy loss log entry.
     *
     * @param source The source of the privacy loss (e.g., "Request", "Broadcast").
     * @param timestamp The timestamp of the event.
     * @param privacyLoss The privacy loss value.
     */
    public void addPrivacyLossLog(String source, String timestamp, float privacyLoss) {
        PrivacyLossEntry entry = new PrivacyLossEntry(source, timestamp, privacyLoss);
        logs.add(entry);
        System.out.println("Privacy loss logged: " + entry);
    }

    /**
     * Retrieves all privacy loss logs.
     *
     * @return List of all privacy loss entries.
     */
    public List<PrivacyLossEntry> getPrivacyLossLogs() {
        return new ArrayList<>(logs);
    }

    /**
     * Calculates the total privacy loss from all logs.
     *
     * @return The total privacy loss value.
     */
    public float getTotalPrivacyLoss() {
        float total = 0.0f;
        for (PrivacyLossEntry entry : logs) {
            total += entry.privacyLoss;
        }
        return total;
    }

    /**
     * Clears all privacy loss logs.
     */
    public void clearLogs() {
        logs.clear();
        System.out.println("All privacy loss logs cleared.");
    }
}
