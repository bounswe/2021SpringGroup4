from .models import EquipmentPost
from rest_framework import serializers


class EquipmentSerializer(serializers.ModelSerializer):
    class Meta:
        model = EquipmentPost
        fields = '__all__'