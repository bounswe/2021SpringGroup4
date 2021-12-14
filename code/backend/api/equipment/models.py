from django.db import models
from api.authentication.models import User 

class EquipmentBody(models.Model):
    title = models.CharField(max_length=50)
    description = models.TextField(max_length=500, blank=True)
    location = models.CharField(max_length=50, blank=True)
    sportType = models.CharField(max_length=30)

    class Meta:
        ordering = ['title']

class Equipment(models.Model):
    @staticmethod
    def context():
        return "https://www.w3.org/ns/activitystreams"
    
    @staticmethod
    def type():
        return "Equipment"

    owner = models.ForeignKey(User, on_delete=models.CASCADE, related_name="equipments")
    body =  models.OneToOneField(EquipmentBody, on_delete=models.CASCADE, related_name="parent")                


