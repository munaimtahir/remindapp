# API Contracts (Optional Backend)

Base URL: `https://<host>/api/v1`

## POST /intake/email
Create a task from an email webhook.
- Body (JSON): `{ "from": "string", "subject": "string", "body": "string" }`
- 201 → `{ "taskId": "uuid", "category": "CAT1|CAT2|CAT3|CAT4" }`

## POST /intake/sms
Create a task from SMS.
- Body (JSON): `{ "from": "string", "text": "string" }`
- 201 → `{ "taskId": "uuid", "category": "CAT1|CAT2|CAT3|CAT4" }`

## POST /ai/categorize
Return category suggestion from free text.
- Body (JSON): `{ "text": "string" }`
- 200 → `{ "category": "CAT1|CAT2|CAT3|CAT4", "confidence": 0.0..1.0 }`
