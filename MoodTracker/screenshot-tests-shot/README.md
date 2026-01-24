# Screenshot Tests Using Shot

This module uses [Shot](https://github.com/pedrovgs/Shot), a screenshot testing library developed that captures screenshots on a device or emulator and compares them against baseline images.

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
./gradlew :screenshot-tests-shot:executeScreenshotTests -Precord
```

The screenshots will be saved to:

```
screenshot-tests-shot/src/screenshots/
```

### Verify Screenshots Against Reference

To run the screenshot tests and compare against golden images:

```bash
./gradlew :screenshot-tests-shot:executeScreenshotTests
```