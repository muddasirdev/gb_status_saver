# GB Status Saver

A native Android app written in Java for browsing and saving status media from WhatsApp and WhatsApp Business.

## What’s in the project

- Fragment-based Android interface with View Binding
- Image and video status previews
- Local media and file handling
- Firebase Realtime Database and Google Mobile Ads dependencies

## Run locally

1. Clone the repository and open it in Android Studio.
2. Configure a Firebase project for your own application ID and add its `google-services.json` locally before building.
3. Sync Gradle and run the `app` configuration on an Android device or emulator (minimum SDK 24).

The repository currently contains an app-specific Firebase configuration file. Replace it with your own project configuration and do not commit private credentials or service account keys.

## Project status

This is an Android sample project. Android storage permissions and media access behavior vary by OS version, so verify the app on the Android versions you intend to support.

## License

No license file is currently included. Contact the repository owner before reusing or distributing this code.
