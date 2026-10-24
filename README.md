# GB Status Saver

A native Android application written in Java for browsing status media through a fragment-based interface. The source includes image/video status handling and local access to media selected by the user.

## Current scope

- Displays image and video status items for supported WhatsApp and WhatsApp Business installations.
- Uses Android's document picker / Storage Access Framework tree selection and stores the selected URI for later access.
- Recognizes common image formats (`jpg`, `jpeg`, `png`) and video files (`mp4`).
- Uses Firebase Realtime Database for an app status/configuration check.
- Includes Google Mobile Ads integration dependencies.

This repository does not contain a backend service. Firebase behavior depends on a Firebase project configured for this app, and ad delivery depends on valid ad configuration.

## Technology

- Java and Android Views
- AndroidX Fragments, View Binding, and Material Components
- Firebase Realtime Database
- Google Mobile Ads
- Android Storage Access Framework

## Project layout

- `app/src/main/java` — activities, fragments, helpers, adapters, and storage code
- `app/src/main/res` — layouts, navigation, drawables, and values
- `app/src/test` and `app/src/androidTest` — test source sets
- `app/build.gradle` — Android configuration and dependencies

## Requirements and setup

Use Android Studio with the Android SDK and a compatible JDK. Open the project and allow Gradle sync to complete. Configure a Firebase project for the app's application ID and use your own local `google-services.json` before running. Configure your own ad application/unit IDs where required.

The repository has historically included app-specific Firebase and signing/configuration files. Treat those as untrusted examples: replace them with your own configuration, rotate any credentials or signing material that may have been exposed, and keep private configuration out of public commits. Do not publish a release signed with a key from this repository.

## Run

Select the `app` configuration in Android Studio and run it on a device or emulator. Storage access and WhatsApp status locations vary by Android release and by the installed WhatsApp variant. The app asks the user to select accessible folders through Android's document picker; test this flow on each supported OS version.

From a shell:

```sh
./gradlew assembleDebug
```

On Windows:

```bat
gradlew.bat assembleDebug
```

No build result is claimed here. Validate the current Gradle and Firebase setup locally before distributing.

## Privacy and platform notes

The app reads media from folders the user grants access to. Verify the requested permissions and data handling against current Android storage rules. WhatsApp package availability, folder layout, and platform policies can change. The app cannot guarantee access to statuses that have expired or were not downloaded to the device.

## License

No license file is included. Contact the repository owner before reuse or distribution.
