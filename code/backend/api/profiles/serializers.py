from api.authentication.models import User 
from rest_framework import serializers
from api.events.models import Event, EventBody
from api.events.serializers import EventSerializer, EventBodySerializer

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'username', 'password', 'email', 'first_name', 'last_name', 
                'age', 'about', 'location', 'going', 'applied', 'following']
        extra_kwargs = {'password': {"write_only": True}}