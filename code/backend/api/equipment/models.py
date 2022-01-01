from django.db import models
from api.authentication.models import User 

class EquipmentPost(models.Model):
    owner = models.ForeignKey(User, on_delete=models.CASCADE, related_name='equipments')
    title = models.CharField(max_length=100)
    description = models.TextField(max_length=500, blank=True, null=True)
    location = models.CharField(max_length=500, blank=True)
    contact = models.CharField(max_length=500, blank=True)
    image_url = models.CharField(max_length=500, blank=True, null=True)
    sportType = models.CharField(max_length=100, blank=True, null=True)

