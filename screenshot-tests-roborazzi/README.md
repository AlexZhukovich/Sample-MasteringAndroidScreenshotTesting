# Screenshot Tests Using Roborazzi

The module uses [Roborazzi](https://github.com/takahirom/roborazzi), a screenshot testing library that captures screenshots on the JVM using Robolectric.

## Module Dependencies

The following feature modules are included for testing:

- `design-system`
- `feature-action-management`
- `feature-home`
- `feature-settings`
- `feature-statistics`

## Running Visual Tests

### Capture Golden (Reference) Screenshots

To generate golden screenshots that will be used as the baseline for comparisons:

```bash
./gradlew :screenshot-tests-roborazzi:recordRoborazziDebug
```

The screenshots will be saved to:

```
screenshot-tests-roborazzi/src/snapshots/
```

### Verify Screenshots Against Reference

To run the screenshot tests and compare against golden images:

```bash
./gradlew :screenshot-tests-roborazzi:verifyRoborazziDebug
```