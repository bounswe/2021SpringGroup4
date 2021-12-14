from rest_framework import serializers
from .models import Notification
from api.authentication.models import User

class NotificationSerializer(serializers.ModelSerializer):
    recipient = serializers.SlugRelatedField(read_only=True, slug_field="username")
    
    class Meta:
        model = Notification
        fields = '__all__'
