# Reminder-Matrix Android App

This is an offline-first Android application for task management, built with modern Android development practices.

## Features (MVP)
- **Task Capture**: Create tasks with a title, description, category, group, and section.
- **Offline-First**: The app is fully functional without an internet connection, using a local Room database.
- **Reminders**: Schedule reminders for tasks, with notifications powered by WorkManager.
- **Digests**: Weekly and daily digest notifications to keep you on track.
- **Secure Settings**: User settings are stored securely using EncryptedSharedPreferences.
- **Modern Tech Stack**: Built with Kotlin, Jetpack Compose, Hilt, Room, and WorkManager.

## How to Build and Run

### Android Studio
1.  Open Android Studio (Giraffe or newer is recommended).
2.  Select `File` > `Open` and choose the `android/` directory of this project.
3.  Allow Gradle to sync the project dependencies.
4.  Create a `local.properties` file in the `android/` directory with the path to your Android SDK, for example:
    ```
    sdk.dir=/Users/your-username/Library/Android/sdk
    ```
5.  Select the `app` run configuration and click the "Run" button.

### Running Tests
You can run the unit and instrumented tests from Android Studio or the command line.

**From Android Studio:**
- Right-click on the `app/src/test` directory and select "Run Tests".
- Right-click on the `app/src/androidTest` directory and select "Run Tests".

**From the command line:**
Navigate to the `android/` directory and run:
```bash
./gradlew test
./gradlew connectedAndroidTest
```
