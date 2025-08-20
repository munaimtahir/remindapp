# Release 1 — MVP (Offline‑First Android)

## Mission
Ship a local‑only Android app for fast task capture with Group/Section assignment, category selection, reminders, and digest workers.

## Features
- Create tasks (text/voice)
- Choose Group and Section from local lists
- Category selection (manual) + placeholder for AI suggestion
- Push reminders via WorkManager (per‑category cadence)
- Weekly Digest (Sun 5 PM) and Daily Snapshot (7 AM weekdays)
- Mark complete/snooze/reschedule
- Simple filters: by Group/Section/Category/Status

## Acceptance Criteria
- App installs and runs on Android 8+
- Creating a task schedules the appropriate reminder worker
- Weekly/Daily workers enqueue correctly
- Data persists across restarts
- 80% unit test coverage for repository + workers

## Test Plan
- Unit: DAO/Repository/UseCases/Workers (JUnit, Mockito, WorkManager TestDriver)
- UI: Compose UI tests (basic create/filter flows)

## Deliverables
- `android/` app module
- `docs/` updated with any design decisions
