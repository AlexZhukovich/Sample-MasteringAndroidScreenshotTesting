# Screenshot Tests Using Dropshots

The module uses [Dropshots](https://github.com/dropbox/dropshots), a screenshot testing library that captures screenshots and compares them against baseline images on a device or emulator.

> All test cases in this module were verified and executed on:
> - **Emulator**: Pixel 4
> - **API Level**: 29

## Module Dependencies

The following feature modules are included for testing:

- `design-system`
- `feature-action-management`
- `feature-home`
- `feature-settings`
- `feature-statistics`

## Running Visual Tests

### Capture Golden (Reference) Screenshots

To capture and save golden screenshots, run:

```bash
./gradlew :screenshot-tests-dropshots:recordDebugAndroidTestScreenshots
```

### Verify Screenshots Against Reference

To run the screenshot tests and compare against golden images:

```bash
./gradlew :screenshot-tests-dropshots:connectedDebugAndroidTest
```