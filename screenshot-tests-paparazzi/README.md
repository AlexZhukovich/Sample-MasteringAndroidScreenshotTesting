# Screenshot Tests Using Paparazzi

The module uses [Paparazzi](https://github.com/cashapp/paparazzi), a screenshot testing library that renders Android screens on the JVM.

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
./gradlew :screenshot-tests-paparazzi:recordPaparazziDebug
```

The screenshots will be saved to:

```
screenshot-tests-paparazzi/src/test/snapshots/
```

### Verify Screenshots Against Reference

To run the screenshot tests and compare against golden images:

```bash
./gradlew :screenshot-tests-paparazzi:verifyPaparazziDebug
```