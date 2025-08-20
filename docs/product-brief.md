# Reminder‑Matrix — Plain‑Language App Description

## What it does
A fast capture Android app that turns short messages (typed or voice) into tasks. Each task is put into a **Group** (e.g., Teachers, Staff) and a **Section** (e.g., Weekly Planning, Events). The app suggests a **Category (Cat1–Cat4)** based on how much preparation time is needed. The category controls the reminder cadence. Users get push notifications and can also receive email reminders if configured. A **Weekly Digest (Sunday 5:00 PM local)** and a **Daily Snapshot (7:00 AM on weekdays)** keep everyone on track.

## Why this design
- **Offline‑first**: Works without the internet; reminders continue via local notifications.
- **Minimal capture**: One‑line input; AI suggestion is optional and can be overridden.
- **Matrix clarity**: You can view tasks by Group, Section, Category, or Status quickly.
- **Digest rhythm**: Weekly and daily summaries prevent surprises.

## Core workflows
1. **Capture**: User types or speaks a short line → task created.
2. **Categorize**: App suggests Cat1–Cat4; user confirms or changes.
3. **Assign**: Choose Group and Section (pre‑defined lists).
4. **Schedule**: Reminders are set by category; user can reschedule or snooze.
5. **Track**: Lists by Group/Section/Category/Status; mark done.
6. **Digest**: Weekly (Sun 5 PM). Daily (7 AM). Configurable later.

## What’s in v1 (MVP)
- Local database (Room), push reminders (WorkManager), simple list UI (Compose),
- Manual Group/Section selection, optional AI suggestion placeholder,
- Weekly/Daily digests as scheduled workers,
- Settings stored securely (EncryptedSharedPreferences).

## What comes later (optional backend)
- Email/SMS intake (create tasks by sending an email/text),
- Server‑side AI categorization API,
- Multi‑device sync.
