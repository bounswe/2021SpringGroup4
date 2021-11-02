from django.db import models
from api.authentication.models import User 

class EventBody(models.Model):
    title = models.CharField(max_length=100)
    description = models.TextField(max_length=500, blank=True) 
    date = models.CharField(max_length=12) # 'DD/MM/YYYY'
    time = models.CharField(max_length=5) # 'HH:MM'
    duration = models.CharField(max_length=5) # 'HH:MM'
    location = models.CharField(max_length=50)
    sportType = models.CharField(max_length=30)
    maxPlayers = models.IntegerField()

    applicants = models.ManyToManyField(User, related_name="applied")
    participants = models.ManyToManyField(User, related_name="going")
    followers = models.ManyToManyField(User, related_name="following")

    class Meta:
        ordering = ['date']

class Event(models.Model):
    @staticmethod
    def context():
        return "https://www.w3.org/ns/activitystreams"

    @staticmethod
    def type():
        return "Event"

    created = models.DateTimeField(auto_now_add=True)
    owner = models.ForeignKey(User, on_delete=models.CASCADE, related_name="events")
    body =  models.OneToOneField(EventBody, on_delete=models.CASCADE)

