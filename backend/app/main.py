from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI(title="Reminder‑Matrix API", version="0.1.0")

class EmailIn(BaseModel):
    from_: str
    subject: str
    body: str

class SMSIn(BaseModel):
    from_: str
    text: str

class CategorizeIn(BaseModel):
    text: str

@app.get("/health")
def health():
    return {"status": "ok"}

@app.post("/intake/email")
def intake_email(payload: EmailIn):
    # TODO: Parse task and persist
    return {"taskId": "stub-email", "category": "CAT2"}

@app.post("/intake/sms")
def intake_sms(payload: SMSIn):
    # TODO: Parse task and persist
    return {"taskId": "stub-sms", "category": "CAT2"}

@app.post("/ai/categorize")
def categorize(payload: CategorizeIn):
    # TODO: Replace with rules/ML
    text = payload.text.lower()
    cat = "CAT2"
    if any(k in text for k in ["urgent", "now", "today", "hour"]):
        cat = "CAT1"
    elif any(k in text for k in ["tomorrow", "next day"]):
        cat = "CAT2"
    elif any(k in text for k in ["in 3 days", "three days"]):
        cat = "CAT3"
    elif any(k in text for k in ["next week", "weeks", "later"]):
        cat = "CAT4"
    return {"category": cat, "confidence": 0.6}
