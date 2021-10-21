from rest_framework import serializers
from .models import Event
from api.authentication.models import User

class UsernameSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('username',)

class EventSerializer(serializers.ModelSerializer):
    creator = serializers.CharField(source='owner.username', read_only=True)
    applicants = serializers.SerializerMethodField()
    participants = serializers.SerializerMethodField()
    followers = serializers.SerializerMethodField()
    class Meta:
        model = Event
        fields = ('id', 'title', 'description', 'creator', 'date', 'time', 'duration', 
                'location', 'sportType', 'numOfPlayers', 'applicants', 'participants', 'followers')

    def create(self, validated_data):
        validated_data['owner'] = self.context['request'].user
        return super(EventSerializer, self).create(validated_data)

    def get_applicants(self, obj):
        return [user.username for user in obj.applicants.all()]

    def get_participants(self, obj):
        return [user.username for user in obj.participants.all()]

    def get_followers(self, obj):
        return [user.username for user in obj.followers.all()]



