from .models import EquipmentPost
from rest_framework import serializers

class EquipmentSerializer(serializers.ModelSerializer):
    creator = serializers.CharField(source='owner.username', read_only=True)
    class Meta:
        model = EquipmentPost
        fields = ['id', 'creator', 'title', 'description', 'location']

    def create(self, validated_data):
        if (self.context['request'].user != None):
            validated_data['owner'] = self.context['request'].user
        return super(EquipmentSerializer, self).create(validated_data)
