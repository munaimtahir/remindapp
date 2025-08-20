# Release 2 — Email & SMS Intake (Optional Backend)

## Mission
Enable creating tasks by sending email/SMS to a designated address/number.

## Features
- FastAPI service with `/intake/email`, `/intake/sms` (stubs)
- Webhook adapters for Mailgun/SendGrid and Twilio (mock)
- Android sync adapter (manual pull) to ingest server‑created tasks

## Acceptance
- Backend runs in Docker
- Posting sample payload creates a task in DB
- Android can fetch and display synced tasks
