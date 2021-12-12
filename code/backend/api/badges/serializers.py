from rest_framework.serializers import Serializer

from .models import Badge  
from api.events.serializers import EventSerializer
from rest_framework.serializers import ModelSerializer
from rest_framework import serializers
from api.authentication.models import User
from activity_handler.serializers import EventObjectSerializer

class BadgeSerializer(ModelSerializer):
    giver = serializers.SlugRelatedField(read_only=True, slug_field="username")
    owner = serializers.SlugRelatedField(queryset=User.objects.all(), slug_field="username")
    event = EventObjectSerializer()
    class Meta:
        model = Badge
        fields = "__all__"