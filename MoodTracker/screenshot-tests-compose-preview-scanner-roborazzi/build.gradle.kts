@file:OptIn(ExperimentalRoborazziApi::class)

import com.github.takahirom.roborazzi.ExperimentalRoborazziApi

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.alexzh.moodtracker.screenshottests.composepreviewscanner.roborazzi"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
            all {
                it.systemProperties["robolectric.pixelCopyRenderMode"] = "hardware"
            }
        }
    }
}

roborazzi {
    outputDir.set(file("src/screenshots"))
    generateComposePreviewRobolectricTests {
        enable = true
        packages = listOf("com.alexzh.designsystem", "com.alexzh.moodtracker")
        robolectricConfig = mapOf(
            "sdk" to "[33]",
            "qualifiers" to "RobolectricDeviceQualifiers.Pixel4",
        )

        // Set a custom ComposePreviewTester. Only one can be set at a time.
        testerQualifiedClassName = "com.alexzh.moodtracker.screenshottests.composepreviewscanner.roborazzi.FakeImageLoaderComposePreviewTester"
//        testerQualifiedClassName = "com.alexzh.moodtracker.screenshottests.composepreviewscanner.roborazzi.InspectionModeComposePreviewTester"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":common-ui"))
    implementation(project(":design-system"))
    implementation(project(":feature-action-management"))
    implementation(project(":feature-home"))
    implementation(project(":feature-settings"))
    implementation(project(":feature-statistics"))
    implementation(project(":feature-widget"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(project(":screenshot-tests-common"))
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.roborazzi.compose.preview.scanner.support)
    testImplementation(libs.roborazzi.rule)
    testImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.composable.preview.scanner.android)
    testImplementation(libs.coil.compose)
    testImplementation(libs.coil.test)

    debugImplementation(libs.androidx.ui.tooling)
}

tasks.withType<Test> {
    dependsOn(
        "compileDebugKotlin",
        ":design-system:compileDebugKotlin",
        ":feature-home:compileDebugKotlin",
        ":feature-settings:compileDebugKotlin",
        ":feature-statistics:compileDebugKotlin",
        ":feature-action-management:compileDebugKotlin",
        ":feature-widget:compileDebugKotlin"
    )
}