# Screenshot Tests Using Composable Preview Scanner and Roborazzi

The module uses the [Composable Preview Scanner](https://github.com/sergio-sastre/ComposablePreviewScanner) to automatically detect composable functions annotated with `@Preview`and uses [Roborazzi](https://github.com/takahirom/roborazzi) to capture screenshots on the JVM.

## Module Dependencies

The following feature modules are included for testing:

- `design-system`
- `feature-action-management`
- `feature-home`
- `feature-settings`
- `feature-statistics`

## Running Visual Tests

### Capture Golden (Reference) Screenshots

To generate golden screenshots from composable function annotated with `@Preview` that will be used as the baseline for comparisons:

```bash
./gradlew :screenshot-tests-compose-preview-scanner-roborazzi:recordRoborazziDebug
```

The screenshots will be saved to:

```
screenshot-tests-compose-preview-scanner-roborazzi/src/test/snapshots/
```

### Verify Screenshots Against Reference

To run the screenshot tests and compare against golden images:

```bash
./gradlew :screenshot-tests-compose-preview-scanner-roborazzi:verifyRoborazziDebug
```