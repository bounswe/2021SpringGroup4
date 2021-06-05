from django.db import models

class User(models.Model):
    username = models.CharField(max_length=30, unique=True)
    hashed_pw = models.CharField(max_length=64) 
    email = models.EmailField(max_length=50, unique=True)
    # TODO: integrate badges
    # TODO: integrate events
    # TODO: token authentication
    description = models.TextField(max_length=250, null=True, blank=True)
    age = models.CharField(max_length=50, null=True, blank=True)
    location = models.CharField(max_length=60, null=True, blank=True)

class Post(models.Model):
    username = models.CharField(max_length=30, unique=True)
    title = models.CharField(max_length=50, unique=True)
    description = models.TextField(max_length=250, null=True, blank=True)
    location = models.CharField(max_length=60, null=True, blank=True)

class EventPost(models.Model):
    eventName = models.CharField(max_length=50, unique=True)
    sportType = models.CharField(max_length=50)
    numOfPlayers = models.IntegerField(null=True, blank=True)
    description = models.CharField(max_length=250, null=True, blank=True)
    date= models.TextField(max_length=250, null=True, blank=True)
    time= models.TextField(max_length=250, null=True, blank=True)
   