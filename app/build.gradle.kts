plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") version "1.9.0" apply true
}

android {
    namespace = "com.main.pdsprototype"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.main.pdsprototype"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("main").jniLibs.srcDirs("src/main/jniLibs")
        getByName("test").jniLibs.srcDirs("src/test/jniLibs")
    }
}

dependencies {
    // Core Android dependencies
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    // Unit testing dependencies
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.7.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    // Instrumented Android tests
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

// Enable JUnit 5 platform
tasks.withType<Test> {
    useJUnitPlatform()

    // Add the JNI library path for the test runtime
    systemProperty("java.library.path", "${projectDir}/src/test/jniLibs/aarch64-darwin")

    // Enable test logging
    testLogging {
        events("passed", "skipped", "failed")
    }
}