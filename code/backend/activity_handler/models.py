from django.db import models
from api.authentication.models import User
from api.events.models import Event, EventBody

class EventActivity(models.Model):
    @staticmethod
    def context():
        return "https://www.w3.org/ns/activitystreams"

    summary = models.CharField(max_length=100)
    type_choices = (
        ("1", "Create"),
        ("2", "Delete"),
        ("3", "Join"),
        ("4", "Apply"),
        ("5", "Update"),
        ("6", "Comment")
    )
    type = models.CharField(choices=type_choices, max_length=10)
    performed_at = models.DateTimeField(auto_now_add=True)
    actor = models.ForeignKey(User, related_name="activities", on_delete=models.SET_NULL, blank=True, null=True)
    object = models.ForeignKey(Event, related_name="activities", on_delete=models.SET_NULL, blank=True,null=True)

    class Meta:
        ordering = ['performed_at']


