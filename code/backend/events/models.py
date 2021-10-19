from django.db import models
from django.contrib.auth.models import User 
from django.utils import timezone

class Event(models.Model):

    sports = (
        ('soccer', 'Soccer'),
        ('basketball', 'Basketball'),
    )

    title = models.CharField(max_length=50)
    description = models.TextField(max_length=200)
    date = models.CharField(max_length=10) # day-month-year -> 01/01/2021#
    time = models.CharField(max_length=5) # ex: '16:15'
    duration = models.CharField(max_length=5) # ex: '02:00'
    creator = models.ForeignKey(User, on_delete=models.CASCADE, related_name='events')
    location = models.CharField(max_length=100) 
    sportType = models.CharField(max_length=50, choices=sports)
    numOfPlayers = models.IntegerField()
    applicants = models.ManyToManyField(User, related_name='appliedTo', blank=True)
    participants = models.ManyToManyField(User, 'participatingIn', blank=True)
    followers = models.ManyToManyField(User, 'followedEvents', blank=True)
    status = models.CharField(max_length=10, default="UPCOMING") # UPCOMING | ONGOING | COMPLETE | CANCELLED
    #TODO: comments 
    #TODO: tags

    class Meta:
        ordering = ('-date', '-time',)

    def __str__(self):
        return self.title

    
    


    