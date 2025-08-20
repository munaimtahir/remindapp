# Architecture Overview

## Mobile (Android)
- **Language/UI**: Kotlin + Jetpack Compose
- **Navigation**: Jetpack Navigation
- **Data**: Room (SQLite), DAO/Repository
- **Background**: WorkManager for reminders + digests with exponential backoff
- **Prefs**: EncryptedSharedPreferences
- **DI**: Hilt
- **Speech**: Android SpeechRecognizer

## Backend (Optional)
- **Framework**: FastAPI (Python)
- **Endpoints**: `/intake/email`, `/intake/sms`, `/ai/categorize`
- **Storage**: PostgreSQL or Firestore (not required for v1)
- **Auth**: API key / OAuth2 for integrations
- **Mail/SMS**: Mailgun/SendGrid, Twilio (stubs provided)

## Data Model (v1)
```kotlin
data class Task(
    val id: String,
    val title: String,
    val description: String?,
    val dueDate: LocalDateTime,
    val category: TaskCategory,
    val groupId: String,
    val sectionId: String,
    val status: TaskStatus,
    val createdAt: LocalDateTime
)

enum class TaskCategory { CAT1, CAT2, CAT3, CAT4 }
enum class TaskStatus { PENDING, COMPLETED, SNOOZED }
```
