# Car Expense Tracker - Android App

A local-first Android app to track car expenses, fuel, maintenance, and documents.

## Local Build Setup

### Prerequisites

1. **Java 17+**
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java17) or [OpenJDK](https://adoptium.net/)
   - Set `JAVA_HOME` environment variable
   - Verify: `java -version`

2. **Android SDK**
   - Install [Android Studio](https://developer.android.com/studio) or command-line tools
   - Required SDK components:
     - Android SDK Platform 34
     - Build-tools 34.0.0
     - Platform-tools
   - Set `ANDROID_SDK_ROOT` environment variable (or `ANDROID_HOME`)
   - On Windows: typically `C:\Users\<username>\AppData\Local\Android\Sdk`

3. **Git**
   - Clone the repository: `git clone <repo-url>`
   - Navigate to project: `cd car-app-android`

### Building Locally

#### Using Gradle Wrapper (Recommended)

The project includes a Gradle wrapper that automatically downloads Gradle 8.2.

**On Windows:**

```bash
gradlew.bat assembleDebug
```

**On Linux/macOS:**

```bash
./gradlew assembleDebug
```

This builds a debug APK at: `app/build/outputs/apk/debug/app-debug.apk`

#### Using Android Studio

1. Open Android Studio
2. File → Open → Select project root directory
3. Wait for Gradle sync to complete
4. Click **Run** or press `Shift+F10`
5. Select emulator or connected device
6. App launches on device

### Common Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (requires keystore)
./gradlew assembleRelease

# Run tests
./gradlew test

# Clean build
./gradlew clean assembleDebug

# View build errors
./gradlew build --stacktrace
```

### Troubleshooting

**"No Android SDK found"**

- Ensure `ANDROID_SDK_ROOT` or `ANDROID_HOME` is set
- Verify SDK paths in Android Studio if using it

**"Gradle sync failed"**

- Run: `./gradlew clean`
- Then: `./gradlew build` to redownload dependencies

**"Module not found: androidx.lifecycle"**

- Verify internet connection
- Clear Gradle cache: `rm -rf ~/.gradle/caches`
- Retry build

**Java version mismatch**

- Ensure Java 17+ is installed and set as `JAVA_HOME`
- Run: `java -version` to verify

## App Features

- **Dashboard**: Overview of expenses, fuel, maintenance, and documents
- **Expenses**: Track general expenses with dates and amounts
- **Fuel**: Log fuel entries with liters, price, and odometer readings
- **Maintenance**: Record maintenance with title, details, and cost
- **Papers**: Store document information with expiry dates

All data is stored locally on device using Room database.

## GitHub Workflow

The project includes automated builds:

- Triggered on tag push (e.g., `git tag v1.0 && git push --tags`)
- Builds debug APK
- Uploads to GitHub releases as artifact
