//package com.main.pdsprototype;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.List;
//
//public class PDSPrototypeTest {
//
//    private CalculatePrivacyLoss calculatePrivacyLoss;
//    private LogPrivacyLoss logPrivacyLoss;
//    private ReturnRequest returnRequest;
//    private ReceiveRequest receiveRequest;
//    private ReceiveBroadcast receiveBroadcast;
//    private ReturnPrivacyLog returnPrivacyLog;
//
//    @BeforeEach
//    void setUp() {
//        // Mock dependencies where needed
//        calculatePrivacyLoss = mock(CalculatePrivacyLoss.class);
//        logPrivacyLoss = new LogPrivacyLoss();
//        returnRequest = new ReturnRequest(new GetLocation());
//        returnPrivacyLog = new ReturnPrivacyLog(logPrivacyLoss);
//
//        // Initialize core classes
//        receiveRequest = new ReceiveRequest(calculatePrivacyLoss, logPrivacyLoss, returnRequest);
//        receiveBroadcast = new ReceiveBroadcast(logPrivacyLoss, returnPrivacyLog);
//    }
//
//    @Test
//    void testLocationRequestFlow() {
//        // Mocking the privacy loss calculation
//        when(calculatePrivacyLoss.computeLoss(5.0f, "Test Metadata")).thenReturn(0.5f);
//
//        // Process a location request
//        receiveRequest.processRequest("location_request", "Test Metadata");
//
//        // Assert the log was updated
//        List<LogPrivacyLoss.PrivacyLossEntry> logs = logPrivacyLoss.getPrivacyLossLogs();
//        assertEquals(1, logs.size());
//        assertEquals(0.5f, logs.get(0).privacyLoss, 0.01);
//        assertEquals("Request", logs.get(0).source);
//
//        // Assert privacy total matches
//        assertEquals(0.5f, logPrivacyLoss.getTotalPrivacyLoss(), 0.01);
//    }
//
//    @Test
//    void testGenericRequestFlow() {
//        // Mocking the privacy loss calculation
//        when(calculatePrivacyLoss.computeLoss(3.0f, "Test Metadata")).thenReturn(0.3f);
//
//        // Process a generic status request
//        receiveRequest.processRequest("status_request", "Test Metadata");
//
//        // Assert the log was updated
//        List<LogPrivacyLoss.PrivacyLossEntry> logs = logPrivacyLoss.getPrivacyLossLogs();
//        assertEquals(1, logs.size());
//        assertEquals(0.3f, logs.get(0).privacyLoss, 0.01);
//        assertEquals("Request", logs.get(0).source);
//
//        // Assert privacy total matches
//        assertEquals(0.3f, logPrivacyLoss.getTotalPrivacyLoss(), 0.01);
//    }
//
//    @Test
//    void testBroadcastFlow() {
//        // Process a broadcast with privacy loss
//        receiveBroadcast.processBroadcast("privacy_beacon", "Test Metadata", 0.2f);
//
//        // Assert the log was updated
//        List<LogPrivacyLoss.PrivacyLossEntry> logs = logPrivacyLoss.getPrivacyLossLogs();
//        assertEquals(1, logs.size());
//        assertEquals(0.2f, logs.get(0).privacyLoss, 0.01);
//        assertEquals("Broadcast", logs.get(0).source);
//
//        // Assert privacy total matches
//        assertEquals(0.2f, logPrivacyLoss.getTotalPrivacyLoss(), 0.01);
//    }
//
//    @Test
//    void testRetrieveLogs() {
//        // Add multiple logs
//        logPrivacyLoss.addPrivacyLossLog("Request", "2024-12-16 10:00:00", 0.1f);
//        logPrivacyLoss.addPrivacyLossLog("Broadcast", "2024-12-16 10:05:00", 0.2f);
//        logPrivacyLoss.addPrivacyLossLog("Request", "2024-12-16 10:10:00", 0.3f);
//
//        // Retrieve and format logs
//        String formattedLogs = returnPrivacyLog.getFormattedPrivacyLogs();
//        assertTrue(formattedLogs.contains("Source: Request"));
//        assertTrue(formattedLogs.contains("Source: Broadcast"));
//        assertTrue(formattedLogs.contains("Privacy Loss: 0.3"));
//    }
//
//    @Test
//    void testRetrieveTotalPrivacyLoss() {
//        // Add multiple logs
//        logPrivacyLoss.addPrivacyLossLog("Request", "2024-12-16 10:00:00", 0.1f);
//        logPrivacyLoss.addPrivacyLossLog("Broadcast", "2024-12-16 10:05:00", 0.2f);
//        logPrivacyLoss.addPrivacyLossLog("Request", "2024-12-16 10:10:00", 0.3f);
//
//        // Retrieve total privacy loss
//        assertEquals(0.6f, logPrivacyLoss.getTotalPrivacyLoss(), 0.01);
//    }
//}
