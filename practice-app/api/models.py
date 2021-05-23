from django.db import models

class User(models.Model):
    username = models.CharField(max_length=30, unique=True)
    hashed_pw = models.CharField(max_length=64) 
    email = models.EmailField(max_length=50, unique=True)
    # TODO: integrate badges
    # TODO: integrate events
    # TODO: token authentication
    description = models.TextField(max_length=250, null=True)
    age = models.CharField(max_length=50, null=True)
    location = models.CharField(max_length=60, null=True)
