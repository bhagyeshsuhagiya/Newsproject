plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.newsproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newsproject"
        minSdk = 24
        targetSdk = 34
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.13.0")
    implementation ("com.google.code.gson:gson:2.11.0")
    implementation ("com.airbnb.android:lottie:6.0.0")
    implementation ("com.google.android.material:material:1.6.0")
    implementation ("com.google.firebase:firebase-auth:22.0.0")
    implementation ("com.airbnb.android:lottie:$6.5.2")
    //noinspection UseTomlInstead
    implementation ("com.google.firebase:firebase-database:20.3.0")// Example version, check for latest
    //noinspection UseTomlInstead
    implementation ("com.google.firebase:firebase-storage:20.2.1")  // Check for the latest version



}