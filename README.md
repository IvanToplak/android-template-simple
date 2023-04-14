![badge][badge-android]
[![Bitrise Build Status](https://app.bitrise.io/app/XYZ/status.svg?token=XYZ&branch=main)](https://app.bitrise.io/app/XYZ)
![GitHub Actions Build Status](https://github.com/IvanToplak/android-template-simple/actions/workflows/push_pr.yml/badge.svg?branch=main)

# Android Template

Android template for Android apps.
[Jetpack Compose](https://developer.android.com/jetpack/compose) is used for building UI components.
UI components are based on [Material Design 3 components](https://m3.material.io/components).

## Architecture

Architecture of this app is MVVM similar to one described in the [official guide](https://developer.android.com/topic/architecture/ui-layer#state-holders).
It follows **Separation of Concerns**, **Single Source of Truth** and **Unidirectional Data Flow** principles.

Main packages:

- **app** - contains Android application class,
- **coroutines** - contains dispatcher definition and coroutine extensions,
- **db** - contains database definition, entities (DB models) and DAOs,
- **di** - contains dependency injection modules and DI initialization,
- **extensions** - contains general extension functions,
- **logging** - contains logging initialization function,
- **mapping** - contains mapping functions between API, DB, and UI models,
- **prefs** - contains user preferences,
- **repository** - contains app repos,
- **service** - contains all API services and models,
- **ui** - contains app theme, navigation, all screens, UI components, and UI models,
- **viewmodel** - contains all view models (VM).

## App Environments

There are 3 environments defined as build flavours:

- **dev** - development environment,
- **qa** - for pre-release testing,
- **prod** - production environment (released app).

All environments have unique application ID (package name) and app name.
This allows having all 3 environments installed on a single device at the same time.

There are 2 build types:

- **debug**: The code is not obfuscated and it also does not remove unused resources. Used during development as the building process is faster.
- **release**: APK/AAB is signed using the debug keystore, since the final signing will be in the CI (Bitrise). The code is obfuscated and the resources optimized to obtain a lighter APK/AAB.

Therefore, there are 6 possible variants: **devDebug**, **devRelease**, **qaDebug**, **qaRelease** ,**prodDebug**, **prodRelease**.

After testing our environments in **debug** mode, it is important to test in **release** mode.
Developers should test the app in **devRelease** variant, and QA should test the app in **qaRelease** variant.

## Firebase

There are 3 Firebase projects (one per environment):

- [[DEV] APP_NAME]() - for development,
- [[QA] APP_NAME]() - for QA testing,
- [APP_NAME]() - for production.

Firebase products:

- [App Distribution]() - easy distribution of pre-releases to test and gather feedback from testers (**qa** environment only),
- [Analytics]() - for reporting on user actions in the app (**prod** environment only),
- [Crashlytics]() - monitor app stability during testing and production (**qa** and **prod** environments).

## Google Play Store

There are 2 apps in the Play Store Console:

- **[QA] APP_NAME** - we use this one only to create integration between Firebase App Distribution and Play Store in order to publish pre-releases for QA testing.
- **APP_NAME** - this is the main app where all setup and releases to production are done.

## Code Style

Code style is defined in the `$HOME/.editorconfig` based on the official Kotlin code style.

Run `ktlint` task to run [Kotlin linter](https://github.com/pinterest/ktlint).

## Dependencies

This is a Jetpack Compose app written in Kotlin with Coroutines and Flow for async work and streams of data.

Other dependencies:

- [Retrofit](https://square.github.io/retrofit/) + [Moshi](https://github.com/square/moshi) for handling HTTP requests,
- [Room](https://developer.android.com/training/data-storage/room) for persisting data locally (local cache),
- [Coil](https://coil-kt.github.io/coil/) for image loading,
- [Koin](https://insert-koin.io/) for dependency injection,
- [Timber](https://github.com/JakeWharton/timber) for logging,
- [Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore) stores and accesses data using keys, useful for storing user preferences.

## CI/CD

This app uses [Bitrise](https://app.bitrise.io/app/XYZ).

Development process:

1. Create new feature branch, add you code, update version per [semantic versioning](https://semver.org/), commit and push.
2. Create a PR. This will trigger a Bitrise build. Build will fail if tests fail or code is not following code style. Do a code review.
3. Merge to `master` branch by using **squash** strategy to create a single commit. This will also trigger a Bitrise build.
4. Create a git tag with the same version as defined in the step 1. It will trigger a Bitrise build and create an artifact (unsigned APK file).
5. Go to **Bitrise build -> Artifacts -> Visit install page** and copy link for sharing with the development team.

[badge-android]: http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat

## License


```
Copyright (c) 2023. [company_name]
All rights reserved.
```
