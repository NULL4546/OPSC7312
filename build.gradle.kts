// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
   // alias(libs.plugins.google.gms.google.services) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("androidx.navigation.safeargs") version "2.8.1" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false

}

buildscript {
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3") // Use the latest stable version
    }
}