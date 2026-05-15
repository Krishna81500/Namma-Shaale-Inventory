# Namma-Shaale Inventory Management System

**Namma-Shaale** (Our School) is an Android-based "Digital Asset Auditor" designed to digitize and streamline asset tracking, health auditing, and maintenance management in government schools.

## 🚀 Features

- **Login & Registration**: Secure access using Email or Unique School ID.
- **Asset Register**: Track sports kits, lab equipment, tablets, and furniture with full metadata (name, category, location, quantity).
- **Monthly Health Check (Rapid Audit)**: Streamlined interface for teachers to perform quick audits (Working, Needs Repair, Broken) for all assets.
- **Issue Logging**: Report problems (e.g., "Football lost", "Tablet screen cracked") with severity levels and reasons.
- **Member Management**: Track school staff, their roles, and bios.
- **Attendance Heatmap**: GitHub-style 30-day visual grid for staff attendance tracking.
- **AI-Powered Audit Reports**: One-tap professional report generation using **Google Gemini 1.5 Pro**.
- **Native Sharing**: Export generated reports via Email, WhatsApp, or other apps.
- **Offline-First**: Built with Room Database to ensure functionality in areas with poor internet.

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Database**: Room DB (SQLite)
- **GenAI**: Gemini 1.5 Pro API
- **Navigation**: Jetpack Compose Navigation
- **Architecture**: MVVM (Model-View-ViewModel)

## 📦 Setup & Installation

1. Clone the repository.
2. Open the project in **Android Studio**.
3. Obtain a **Gemini API Key** from [Google AI Studio](https://aistudio.google.com/).
4. Replace `YOUR_GEMINI_API_KEY` in `AssetViewModel.kt` with your actual key.
5. Sync Gradle and run the app on an emulator or physical device.

## 📷 Success Criteria Met

- [x] Teachers can update 10+ items in under 2 minutes using Rapid Audit.
- [x] Generates professional summary reports ready for SDMC submission.
- [x] Professional, organized, and accessible UI.
- [x] Full persistence of data across app restarts.

---
*Developed as part of the Android App Development using GenAI initiative - April 2026*
