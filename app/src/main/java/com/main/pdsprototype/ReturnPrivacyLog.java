package com.main.pdsprototype;

import java.util.List;

/**
 * ReturnPrivacyLog retrieves and transmits privacy logs to CityOS or requesting applications.
 */
public class ReturnPrivacyLog {

    private final LogPrivacyLoss logPrivacyLoss;

    /**
     * Constructor initializes the dependency on LogPrivacyLoss.
     *
     * @param logPrivacyLoss The LogPrivacyLoss instance used to access stored logs.
     */
    public ReturnPrivacyLog(LogPrivacyLoss logPrivacyLoss) {
        this.logPrivacyLoss = logPrivacyLoss;
    }

    /**
     * Retrieves all privacy loss logs and formats them for transmission.
     *
     * @return A formatted string representation of all logs.
     */
    public String getFormattedPrivacyLogs() {
        List<LogPrivacyLoss.PrivacyLossEntry> logs = logPrivacyLoss.getPrivacyLossLogs();
        StringBuilder formattedLogs = new StringBuilder();

        for (LogPrivacyLoss.PrivacyLossEntry entry : logs) {
            formattedLogs.append(entry.toString()).append("\n");
        }

        return formattedLogs.toString();
    }

    /**
     * Retrieves the user's total privacy loss.
     * (Placeholder: Implement the actual transmission logic here.)
     */
    public float sendTotalPrivacyLoss() {
        float totalPrivacyLoss = logPrivacyLoss.getTotalPrivacyLoss();
        System.out.println("Total privacy loss: " + totalPrivacyLoss);

        // TODO: Implement network transmission (HttpURLConnection)

        return totalPrivacyLoss;
    }

    /**
     * Transmits the formatted privacy logs to a server.
     * (Placeholder: Implement the actual transmission logic here.)
     */
    public void sendPrivacyLogsToServer() {
        String formattedLogs = getFormattedPrivacyLogs();
        // Placeholder: Replace with actual network transmission logic
        System.out.println("Sending privacy logs to server:");
        System.out.println(formattedLogs);

        // TODO: Implement network transmission (HttpURLConnection)
    }

    /**
     * Clears all privacy logs after transmission.
     */
    public void clearLogsAfterTransmission() {
        logPrivacyLoss.clearLogs();
        System.out.println("Privacy logs cleared after transmission.");
    }


}
