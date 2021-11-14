import enum
from django.db import models
from django.db.models.fields import TextField
from django.db.models.fields.related import ForeignKey
from api.authentication.models import User


class NotificationTEMPLATES():
    greeting = 'Welcome to SportsHUB! Your registration was successful!'
 

class Notification(models.Model):
    recipient = ForeignKey(User, on_delete=models.CASCADE, related_name="notifications")
    body = TextField(null=False)
    date = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.body
