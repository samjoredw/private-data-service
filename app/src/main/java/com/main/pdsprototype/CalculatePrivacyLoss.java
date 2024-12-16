package com.main.pdsprototype;

public class CalculatePrivacyLoss {
    private static boolean isTestMode = false;
    private static float testValue = 0.1f;

    // Static method to enable test mode
    public static void enableTestMode(float mockReturnValue) {
        isTestMode = true;
        testValue = mockReturnValue;
    }

    // Static method to disable test mode
    public static void disableTestMode() {
        isTestMode = false;
    }

    // Native method declaration (implemented in Rust)
    public native float calculatePrivacyLoss(float precision);

    /**
     * Public method to calculate privacy loss.
     */
    public float computeLoss(float precision, String metadata) {
        if (precision <= 0) {
            throw new IllegalArgumentException("Precision must be a positive value.");
        }

        float privacyLoss;
        if (isTestMode) {
            privacyLoss = precision * testValue;
        } else {
            privacyLoss = calculatePrivacyLoss(precision);
        }

        System.out.println("Privacy Loss Calculated: " + privacyLoss +
                " | Precision: " + precision + " | Metadata: " + metadata);

        return privacyLoss;
    }
}