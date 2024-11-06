PDSPrototype - Private Data Service Prototype

Description:
Implements a prototype for a Private Data Service (PDS) in an Android application, which calculates privacy loss based on location data. The PDS listens for specific broadcast intents and updates privacy loss calculations based on simulated location data and interactions.

Project Structure:
- app/src/main/java/com/main/pdsprototype/PDSPrototype.java       // Core service file handling location updates and privacy loss
- app/src/main/java/com/main/pdsprototype/MainActivity.java       // Main launcher activity for the application
- AndroidManifest.xml                                             // Manifest file defining permissions and services

Required Permissions:
- ACCESS_FINE_LOCATION
- ACCESS_WIFI_STATE
- INTERNET
(Depending on what you are attempting to test)

Setup Instructions:
1. Clone
2. Install Android SDK
3. Connect an emulator (Android Studio is preferred)

How to Test:
1. Launch the app on the emulator. This should start MainActivity and initialize PDSPrototype.
2. Open a terminal and use the following command to send a test broadcast:
   adb shell am broadcast -a com.cityos.STORE_LOCATION --es appIdentifier "testApp"
Expected Results:
   - A toast message should appear on the emulator with the message: "Received Broadcast: com.cityos.STORE_LOCATION, App ID: testApp".
   - Logcat should display messages confirming the broadcast reception and privacy loss calculation.
   - Logcat can be tricky; If you are unable to use Logcat, an alternative is piping output to a txt file.

How to Simulate Location:
1. TODO

Note:
Ensure that "privacy_lib" (the Rust library for privacy calculations) is loaded correctly. Replace "privacy_lib" in System.loadLibrary() with the actual library name if necessary.

Additional Commands:
- adb devices                 // Verify if emulator is connected
- adb logcat                  // Open Logcat in terminal for debugging
- adb shell am broadcast -a com.cityos.STORE_LOCATION --es appIdentifier "testApp"
