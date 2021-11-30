from django.db import models
from api.authentication.models import User 

class EventBody(models.Model):
    title = models.CharField(max_length=100)
    description = models.TextField(max_length=500, blank=True) 
    date = models.DateField() 
    time = models.TimeField() 
    duration = models.TimeField() 
    location = models.CharField(max_length=50)
    sportType = models.CharField(max_length=30)
    maxPlayers = models.IntegerField()
    applicants = models.ManyToManyField(User, related_name="applied")
    participants = models.ManyToManyField(User, related_name="going")
    SKILL_LEVELS =(
    ("1","Beginner"),
    ("2","Intermediate"),
    ("3", "Advanced"))

    skill_level = models.CharField(max_length=10,choices=SKILL_LEVELS, default="Beginner")

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
    body =  models.OneToOneField(EventBody, on_delete=models.CASCADE, related_name="parent")

class Comment(models.Model):
    posted = models.DateTimeField(auto_now_add=True)
    owner = models.ForeignKey(User, on_delete=models.CASCADE, related_name="event_comments")
    parent = models.ForeignKey(EventBody, on_delete=models.CASCADE, related_name="comments")
    body = models.TextField(max_length=200)

    #TODO: upvote/downvote mechanism 

    class Meta:
        ordering = ['posted']
