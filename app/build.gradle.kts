plugins {
    id("com.android.application")
}

android {
    namespace = "com.main.pdsprototype"  // Define the namespace here
    compileSdk = 33

    defaultConfig {
        applicationId = "com.main.pdsprototype"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        // Required for location permission handling on Android 6.0+
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    // Testing dependencies (optional)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
