plugins {
    alias(libs.plugins.android.application)
    // (kein Kotlin‑Android‑Plugin nötig, wenn du nur Java schreibst)
}

val BUILD_API_KEY: String by project

android {
    namespace = "com.example.fixapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fixapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // so erzeugt Gradle in BuildConfig.BUILD_API_KEY deine URL als String
        buildConfigField("String", "BUILD_API_KEY", "\"$BUILD_API_KEY\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // String‑Notation in Kotlin‑DSL:
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
