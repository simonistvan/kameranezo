plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.kameranezo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.kameranezo"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation("com.google.firebase:firebase-auth:21.1.0")
    implementation("com.google.firebase:firebase-firestore:24.9.1")
    implementation("com.airbnb.android:lottie:5.0.3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    implementation("com.google.android.exoplayer:exoplayer:2.19.0")

    implementation("androidx.recyclerview:recyclerview:1.3.0")

}
