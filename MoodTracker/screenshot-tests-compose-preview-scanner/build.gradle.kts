plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.alexzh.moodtracker.screenshottests.composepreviewscanner"
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
            all { test ->
                test.testLogging {
                    showStandardStreams = true
                }
            }
        }
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

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    testImplementation(libs.composable.preview.scanner.android)

    debugImplementation(libs.androidx.ui.tooling)
}

tasks.withType<Test> {
    dependsOn(
        "compileDebugKotlin",
        ":design-system:compileDebugKotlin",
        ":feature-home:compileDebugKotlin",
        ":feature-settings:compileDebugKotlin",
        ":feature-statistics:compileDebugKotlin",
        ":feature-action-management:compileDebugKotlin"
    )

    // Required for Paparazzi to find resources
    systemProperty("paparazzi.test.resources", "true")
}