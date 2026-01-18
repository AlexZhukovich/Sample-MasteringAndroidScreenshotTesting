pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "MoodTracker"
include(":app")
include(":core")
include(":common-ui")
include(":design-system")
include(":feature-action-management")
include(":feature-home")
include(":feature-settings")
include(":feature-statistics")
include(":screenshot-tests-android-testify")
include(":screenshot-tests-compose-preview")
include(":screenshot-tests-dropshots")
include(":screenshot-tests-paparazzi")
include(":screenshot-tests-roborazzi")
include(":screenshot-tests-shot")
include(":screenshot-tests-compose-preview-scanner")
