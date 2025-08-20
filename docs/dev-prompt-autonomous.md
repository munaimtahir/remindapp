=== Autonomous Dev Prompt: Reminder‑Matrix (Release 1) ===

ROLE: You are an autonomous Kotlin Android engineer. Build the MVP as described below.

MISSION:
- Offline‑first Android app with: task capture (text/voice), Group/Section assignment, manual category selection with placeholder AI suggestion, per‑category reminders via WorkManager, Weekly (Sun 5 PM) & Daily (7 AM weekdays) digest workers, basic filters, and status updates.

TECH CONSTRAINTS:
- Kotlin + Jetpack Compose + Navigation
- Room + Repository
- WorkManager for reminders/digests with exponential backoff
- EncryptedSharedPreferences for settings
- Hilt DI
- Min SDK 26 (Android 8)
- Unit tests with JUnit/Mockito, worker tests with TestDriver, basic Compose tests

CATEGORY CADENCE (default, configurable later):
- CAT1 (≤4h): remind at creation+1h, then hourly until due
- CAT2 (≥1d): remind at T-24h, then at due
- CAT3 (≥3d): remind at T-72h, T-24h, then at due
- CAT4 (>4d): remind weekly until inside 3d window, then follow CAT3

TASKS:
1) Project bootstrap (Gradle, Hilt, Navigation, Compose, Room, WorkManager)
2) Data layer: Task entity, DAO, Repository, sample Groups/Sections
3) Use cases: create task, schedule reminders, snooze/reschedule, complete
4) Workers: ReminderWorker (per task), WeeklyDigestWorker, DailySnapshotWorker
5) UI: inbox list, add task sheet (text/voice), filters, detail screen with status
6) Settings: digest times (default Sun 17:00 & 07:00 weekdays; local time)
7) Tests: repository & workers (>=80% coverage target), minimal UI test
8) Lint/format: ktlint, detekt

DELIVERABLES:
- Code in `android/` matching this repo layout
- README with build/run instructions
- Sample data provider for Groups/Sections

ACCEPTANCE CHECKS:
- Create task → schedules reminder(s) per category
- Workers enqueue and run with TestDriver in tests
- App state survives process death (Room/State management)
