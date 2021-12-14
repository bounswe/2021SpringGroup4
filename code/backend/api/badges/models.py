from django.db import models
from api.events.models import Event
from api.authentication.models import User 

class Badge(models.Model):
    giver = models.ForeignKey(User, related_name="badges_granted", on_delete=models.CASCADE)
    owner = models.ForeignKey(User, related_name="badges", on_delete=models.CASCADE)
    choices = (
        ("1", "Skilled"),
        ("2", "Friendly"),
        ("3", "Master"),
        ("4", "Novice"),
        ("5", "Top Organizer"),
        ("6", "Sore Loser"),
        ("7", "Crybaby"),
        ("8", "Gentleman")
    )
    type = models.CharField(choices=choices, max_length=20)
    event = models.ForeignKey(Event, related_name="badges", on_delete=models.CASCADE)