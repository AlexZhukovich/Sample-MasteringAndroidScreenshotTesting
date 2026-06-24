import org.gradle.kotlin.dsl.assign
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.screenshot)
}

android {
    namespace = "com.alexzh.moodtracker.screenshottests.composepreview"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget("17")
        }
    }
    buildFeatures {
        compose = true
    }
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

dependencies {
    implementation(project(":core"))
    implementation(project(":common-ui"))
    implementation(project(":design-system"))
    implementation(project(":feature-action-management"))
    implementation(project(":feature-home"))
    implementation(project(":feature-settings"))
    implementation(project(":feature-statistics"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    screenshotTestImplementation(libs.androidx.ui.tooling)
    screenshotTestImplementation(libs.screenshot.validation.api)
    screenshotTestImplementation(libs.androidx.compose.material3.adaptive)
    screenshotTestImplementation(libs.androidx.compose.material3.adaptive.layout)
    screenshotTestImplementation(libs.androidx.compose.material3.adaptive.navigation)
    screenshotTestImplementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    screenshotTestImplementation(platform(libs.androidx.compose.bom))
    screenshotTestImplementation(libs.androidx.core.ktx)
    screenshotTestImplementation(libs.androidx.ui)
    screenshotTestImplementation(libs.androidx.ui.graphics)
    screenshotTestImplementation(libs.androidx.ui.tooling.preview)
    screenshotTestImplementation(libs.androidx.material3)
    screenshotTestImplementation(libs.coil.compose)
    screenshotTestImplementation(libs.coil.test)
}