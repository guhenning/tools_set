import os
import smtplib
from email.message import EmailMessage
from dotenv import load_dotenv

load_dotenv()
EMAIL = os.environ.get("EMAIL")
EMAIL_PASSWORD = os.environ.get("EMAIL_PASSWORD")


def email_sender(subject, body, to):
    msg = EmailMessage()
    msg.set_content(body)
    msg["subject"] = subject
    msg["to"] = to
    msg["from"] = EMAIL

    server = smtplib.SMTP("smtp.gmail.com", 587)
    server.starttls()
    server.login(EMAIL, EMAIL_PASSWORD)
    server.send_message(msg)
    server.quit()


if __name__ == "__main__":
    email_sender("subject", "message", "reciever_email_here")
