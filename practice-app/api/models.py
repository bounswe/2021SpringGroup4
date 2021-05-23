from django.db import models

class User(models.Model):
    username = models.CharField(max_length=30, unique=True)
    password_hash = models.CharField(max_length=256) 
    email = models.EmailField(max_length=50, unique=True)
    # to do: integrate badges
    # to do: integrate events
    # to do: token authentication
    description = models.TextField(max_length=250, null=True)
    age = models.CharField(max_length=50, null=True)
    location = models.CharField(max_length=60, null=True)
