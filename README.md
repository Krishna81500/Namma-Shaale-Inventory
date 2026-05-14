# Namma Shaale Inventory

[![GitHub Repo stars](https://img.shields.io/github/stars/Krishna81500/Namma-Shaale-Inventory?style=social)](https://github.com/Krishna81500/Namma-Shaale-Inventory)
[![GitHub issues](https://img.shields.io/github/issues/Krishna81500/Namma-Shaale-Inventory)](https://github.com/Krishna81500/Namma-Shaale-Inventory/issues)
[![GitHub license](https://img.shields.io/badge/license-Educational-blue)](https://github.com/Krishna81500/Namma-Shaale-Inventory)

Namma Shaale is an Android asset management application designed for schools to manage inventory, attendance, issue logs, and audit records. The app is built with Kotlin, Jetpack Compose, Room database, MVVM architecture, and includes a GenAI-powered report generation feature.

## Project Features

- Asset management with add, update, view, and delete capabilities
- Member and attendance tracking
- Issue logging and condition history tracking
- AI-powered report generation using Google Gemini integration
- Secure login and member registration
- Clean UI built with Jetpack Compose and Material Design 3
- Local data persistence with Room SQLite database

## Technologies Used

- Kotlin
- Jetpack Compose
- Android Architecture Components (ViewModel, LiveData, Navigation)
- Room Database
- Coroutines
- Google Gemini AI integration
- Gradle (Kotlin DSL)
- Material Design 3

## Repository Contents

- `src/main/java` - Application source code
- `src/main/res` - Android resources (layouts, drawables, themes)
- `project_report.html` - Detailed engineering report for the project
- `build.gradle.kts` - Gradle build configuration
- `AndroidManifest.xml` - App manifest configuration
- `README.md` - Project documentation
- `screenshots/` - Optional screenshot assets for the app

## Getting Started

1. Clone this repository:
   ```bash
   git clone https://github.com/Krishna81500/Namma-Shaale-Inventory.git
   ```

2. Open the project in Android Studio.

3. Sync Gradle and build the project.

4. Run the app on an emulator or Android device with API level 24 or higher.

## App Screenshots

Add your screenshots to the `/screenshots` folder and reference them below for a richer README:

- `screenshots/dashboard.png` - main dashboard view
- `screenshots/asset_list.png` - asset management screen
- `screenshots/report_generation.png` - AI-powered report generation screen

> Example image markdown:
> ```md
> ![Dashboard](screenshots/dashboard.png)
> ```

## How to Use

- Launch the app and register a new member or login with existing credentials.
- Use the dashboard to manage assets and view reports.
- Add or update asset details from the asset management screen.
- Record attendance and track issue logs in the respective screens.
- Generate a project audit report through the GenAI-powered report screen.

## Development Notes

- App architecture follows MVVM with Room for local persistence and Jetpack Compose for UI.
- `AssetViewModel` uses Kotlin Coroutines and `StateFlow` for reactive UI updates.
- `GeminiReportGenerator` integrates Google Gemini for smart audit and report creation.
- Use Android Studio Arctic Fox or later for best compatibility.
- Target API level 24+ and compile using Android SDK 36.

## License

This project is provided as-is for educational and demonstration purposes.
