# Screenshot Tests Using Android Testify

The module uses [Android Testify](https://ndtp.github.io/android-testify/), a screenshot testing library that captures screenshots on a real device or emulator and compares them against baseline images.

## Module Dependencies

The following feature modules are included for testing:

- `design-system`
- `feature-action-management`
- `feature-home`
- `feature-settings`
- `feature-statistics`

## Prerequisites

Before running screenshot tests, ensure you:

- Disable animations on the connected device (you can do it in *Developer Options*):
  - Window animation scale: Off
  - Transition animation scale: Off
  - Animator duration scale: Off

## Running Visual Tests

### Capture Golden (Reference) Screenshots

To capture and save golden screenshots, run:

```bash
./gradlew :screenshot-tests-android-testify:screenshotRecord
```

This command will:
1. Build and install the test APK on the connected device
2. Run all screenshot tests
3. Capture screenshots for each test
4. Pull the screenshots from the device
5. Save them as reference images in `screenshot-tests-android-testify/src/androidTest/assets/`

### Verify Screenshots Against Reference

To run the screenshot tests and compare against golden images:

```bash
./gradlew :screenshot-tests-android-testify:screenshotTest
```