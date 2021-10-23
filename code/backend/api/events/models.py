from django.db import models
from api.authentication.models import User 

class Event(models.Model):
    title = models.CharField(max_length=100)
    description = models.TextField(max_length=500, blank=True) 
    owner = models.ForeignKey(User, on_delete=models.CASCADE, related_name="events")
    date = models.CharField(max_length=12) # 'DD/MM/YYYY'
    time = models.CharField(max_length=5) # 'HH:MM'
    duration = models.CharField(max_length=5) # 'HH:MM'
    location = models.CharField(max_length=50)
    sportType = models.CharField(max_length=30)
    numOfPlayers = models.IntegerField(default=0)

    applicants = models.ManyToManyField(User, related_name="applied_to")
    participants = models.ManyToManyField(User, related_name="going_to")
    followers = models.ManyToManyField(User, related_name="following")

    #TODO: comments

    class Meta:
        ordering = ['date']



