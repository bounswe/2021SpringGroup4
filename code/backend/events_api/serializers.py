from rest_framework import serializers
from events.models import Event

class EventSerializer(serializers.ModelSerializer):
    class Meta:
        model = Event 
        fields = ('id', 'title', 'description', 'date', 'time', 
                'duration', 'creator', 'location', 'sportType', 
                'numOfPlayers', 'participants', 'applicants', 'followers', 'status')
        