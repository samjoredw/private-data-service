package com.main.pdsprototype;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.mockito.MockedStatic;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class DemoTest {
    private static MockedStatic<android.util.Log> mockedLog;
    private static PrintWriter logWriter;
    private static final String LOG_FILE = "demo-test-output.txt";

    @BeforeAll
    public static void setup() {
        try {
            logWriter = new PrintWriter(new FileWriter(LOG_FILE));
            mockedLog = TestLogger.mockLogger();
            CalculatePrivacyLoss.enableTestMode(0.1f);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create log file", e);
        }
    }

    @AfterAll
    public static void cleanup() {
        if (mockedLog != null) {
            mockedLog.close();
        }
        if (logWriter != null) {
            logWriter.close();
        }
        CalculatePrivacyLoss.disableTestMode();
    }

    private void log(String message) {
        System.out.println(message);  // Still print to console
        logWriter.println(message);   // Also write to file
        logWriter.flush();           // Ensure it's written immediately
    }

    @Test
    public void runDemos() {
        // Initialize components
        CalculatePrivacyLoss calculatePrivacyLoss = new CalculatePrivacyLoss();
        LogPrivacyLoss logPrivacyLoss = new LogPrivacyLoss();
        GetLocation getLocation = new GetLocation();
        ReturnRequest returnRequest = new ReturnRequest(getLocation);
        ReturnPrivacyLog returnPrivacyLog = new ReturnPrivacyLog(logPrivacyLoss);
        ReceiveRequest receiveRequest = new ReceiveRequest(calculatePrivacyLoss, logPrivacyLoss, returnRequest);
        ReceiveBroadcast receiveBroadcast = new ReceiveBroadcast(logPrivacyLoss, returnPrivacyLog);

        log("=== Demo 1: Process Location Request ===");
        demoProcessLocationRequest(receiveRequest, logPrivacyLoss);

        log("\n=== Demo 2: Process General Request ===");
        demoProcessGeneralRequest(receiveRequest, logPrivacyLoss);

        log("\n=== Demo 3: Process Broadcast ===");
        demoProcessBroadcast(receiveBroadcast, logPrivacyLoss);

        // Generate additional test events
        generateAdditionalLogs(receiveRequest, receiveBroadcast, logPrivacyLoss);

        log("\n=== Demo 4: Retrieve Privacy Logs ===");
        demoRetrieveLogs(returnPrivacyLog);

        log("\n=== Demo 5: Retrieve Total Privacy Loss ===");
        demoRetrieveTotalPrivacyLoss(returnPrivacyLog);
    }

    private void generateAdditionalLogs(ReceiveRequest receiveRequest, ReceiveBroadcast receiveBroadcast, LogPrivacyLoss logPrivacyLoss) {
        // Additional location requests
        receiveRequest.processRequest("location_request", "High Precision Location Update");
        receiveRequest.processRequest("location_request", "Emergency Location Request");

        // Additional status requests
        receiveRequest.processRequest("status_request", "Routine Status Check");

        // Additional broadcasts with varying privacy losses
        receiveBroadcast.processBroadcast("privacy_beacon", "Network Update", 0.3f);
        receiveBroadcast.processBroadcast("aggregate_data", "Aggregate Data Update", 0.2f);
    }

    private void demoProcessLocationRequest(ReceiveRequest receiveRequest, LogPrivacyLoss logPrivacyLoss) {
        log("\nProcessing a location request from CityOS. This calculates privacy loss based on location precision.");
        log("Parameters:");
        log("- Request Type: location_request");
        log("- Location Precision: 5.0");
        log("----------------------------------------");
        log("Privacy Total Before Request: " + logPrivacyLoss.getTotalPrivacyLoss());
        receiveRequest.processRequest("location_request", "Mock Metadata for Location");
        log("Privacy Total After Request: " + logPrivacyLoss.getTotalPrivacyLoss());
        log("----------------------------------------");
    }

    private void demoProcessGeneralRequest(ReceiveRequest receiveRequest, LogPrivacyLoss logPrivacyLoss) {
        log("\nProcessing a general status request. This has lower privacy impact than location requests.");
        log("Parameters:");
        log("- Request Type: status_request");
        log("- Status Precision: 3.0");
        log("----------------------------------------");
        log("Privacy Total Before Request: " + logPrivacyLoss.getTotalPrivacyLoss());
        receiveRequest.processRequest("status_request", "Mock Metadata for General Request");
        log("Privacy Total After Request: " + logPrivacyLoss.getTotalPrivacyLoss());
        log("----------------------------------------");
    }

    private void demoProcessBroadcast(ReceiveBroadcast receiveBroadcast, LogPrivacyLoss logPrivacyLoss) {
        log("\nReceiving a broadcast from a privacy beacon with predefined privacy loss.");
        log("Parameters:");
        log("- Broadcast Type: privacy_beacon");
        log("- Privacy Loss: 0.4");
        log("----------------------------------------");
        log("Privacy Total Before Broadcast: " + logPrivacyLoss.getTotalPrivacyLoss());
        receiveBroadcast.processBroadcast("privacy_beacon", "Mock Metadata for Broadcast", 0.4f);
        log("Privacy Total After Broadcast: " + logPrivacyLoss.getTotalPrivacyLoss());
        log("----------------------------------------");
    }

    private void demoRetrieveLogs(ReturnPrivacyLog returnPrivacyLog) {
        log("\nDisplaying all recorded privacy-impacting events.");
        log("This includes all requests and broadcasts with their associated privacy losses.");
        log("Events shown include both demo operations and background system activities.");
        log("----------------------------------------");
        log("Retrieved Privacy Logs:");
        String logs = returnPrivacyLog.getFormattedPrivacyLogs();
        log(logs.isEmpty() ? "No logs available." : logs);
        log("----------------------------------------");
    }

    private void demoRetrieveTotalPrivacyLoss(ReturnPrivacyLog returnPrivacyLog) {
        log("\nCalculating total privacy impact across all operations.");
        log("This represents the cumulative privacy loss from all logged events.");
        log("----------------------------------------");
        log("Current Total Privacy Loss: " + returnPrivacyLog.sendTotalPrivacyLoss());
        log("----------------------------------------");
    }
}