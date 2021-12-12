from api.authentication.models import User 
from rest_framework import serializers
from api.events.models import Event, EventBody
from api.events.serializers import EventSerializer, EventBodySerializer
from api.badges.serializers import BadgeSerializer

class UserSerializer(serializers.ModelSerializer):
    badges = BadgeSerializer(many=True, read_only=True)
    class Meta:
        model = User
        fields = ['id', 'username', 'password', 'email', 'first_name', 'last_name', 
                'age', 'about', 'location', 'going', 'applied', 'profile_picture', 'badges']
        extra_kwargs = {'password': {"write_only": True}}