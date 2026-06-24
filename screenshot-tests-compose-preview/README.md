# Screenshot Tests Using Compose Preview

This module uses [Compose Preview Screenshot Testing](https://developer.android.com/studio/preview/compose-screenshot-testing), an official Android library that generates screenshot tests from `@Preview` functions.

## Module Dependencies

The following feature modules are included for testing:

- `design-system`
- `feature-action-management`
- `feature-home`
- `feature-settings`
- `feature-statistics`

## Running Visual Tests

### Generate Golden (Reference) Screenshots

To generate golden screenshots that will be used as the baseline for comparisons:

```bash
./gradlew :screenshot-tests-compose-preview:updateDebugScreenshotTest
```

This command scans all `@Preview` annotated composable functions in the `screenshotTest` source set and saves screenshots to:
```
screenshot-tests-compose-preview/src/screenshotTest/reference/
```

### Verify Screenshots Against Reference

To run the screenshot tests and compare against golden images:

```bash
./gradlew :screenshot-tests-compose-preview:validateDebugScreenshotTest
```