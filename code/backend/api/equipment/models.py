from django.db import models
from api.authentication.models import User 

class EquipmentPost(models.Model):
    owner = models.ForeignKey(User, on_delete=models.CASCADE, related_name='equipments')
    title = models.CharField(max_length=50)
    description = models.TextField(max_length=500, blank=True)
    location = models.CharField(max_length=50, blank=True) 


