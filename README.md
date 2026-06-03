# Mastering Android Screenshot Testing: Sample

> Companion demo application for [Mastering Android Screenshot Testing](https://leanpub.com/mastering-android-screenshot-testing) book.

This repository contains the source code for the Mood Tracker application used in the book to demonstrate how to implement screenshot tests for an Android application. It is intended for educational purposes only and is not meant for production use.

## Mood Tracker

Mood Tracker is a minimalistic mood-tracking app that shows how screenshot tests created using different frameworks can be added to a real Android project. Every screenshot test described in the book can be found here.

To compare frameworks side by side, each screenshot testing framework lives in its own module. A real project would not be structured this way, but isolating each framework makes it easier to see how each one is configured and to navigate the test cases.

### Modules

The application follows a multi-module architecture:

- **app**: the main application module that connects all feature modules.
- **core**: shared data and domain layers.
- **common-ui**: shared drawable and string resources.
- **design-system**: reusable UI components and the application theme.
- **feature-action-management**: managing action categories and actions.
- **feature-home**: home screen and managing the mood entities.
- **feature-settings**: managing app preferences.
- **feature-statistics**: statistics based on user data.
- **feature-widget**: a home screen widget.

**Screenshot testing modules**

The **screenshot-tests-common** provides annotations, `FakeImageLoaderRule` class, and other shared utilities used across the screenshot testing modules.

Each of the following modules implements the test cases (covering the design-system and feature modules) using a different framework or tool:

- **screenshot-tests-android-testify**: Android Testify
- **screenshot-tests-compose-preview**: Compose Preview Screenshot Testing
- **screenshot-tests-compose-preview-scanner-paparazzi**: ComposablePreviewScanner with Paparazzi
- **screenshot-tests-compose-preview-scanner-roborazzi**: ComposablePreviewScanner with Roborazzi
- **screenshot-tests-dropshots**: Dropshots & AndroidUiTestingUtils with Dropshots
- **screenshot-tests-paparazzi**: Paparazzi
- **screenshot-tests-roborazzi**: Roborazzi
- **screenshot-tests-shot**: Shot 
  
Each of these modules includes its own README.md with the prerequisites, the commands for running its screenshot tests, and the device used for instrumentation tests.
