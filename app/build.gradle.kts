plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs") version "2.8.1" apply false
    id("com.google.gms.google-services")
    id ("kotlin-android")
    id ("androidx.navigation.safeargs.kotlin") // This enables Safe Args for Kotlin
}

android {
    namespace = "com.example.notesapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.notesapplication"
        minSdk = 30
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("androidx.biometric:biometric:1.1.0")

    implementation ("com.google.android.gms:play-services-auth:20.5.0")
    implementation ("com.google.firebase:firebase-auth:22.1.1")


    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")


    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.8.1"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}