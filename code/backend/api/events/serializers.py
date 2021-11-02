from rest_framework import serializers
from .models import Event, EventBody
from api.authentication.models import User
from rest_framework.response import Response
from collections import OrderedDict

class EventBodySerializer(serializers.ModelSerializer):
    applicants = serializers.SlugRelatedField(many=True, read_only=True, slug_field="username")
    participants = serializers.SlugRelatedField(many=True, read_only=True, slug_field="username")
    followers = serializers.SlugRelatedField(many=True, read_only=True, slug_field="username")
    class Meta:
        model = EventBody
        fields = ('title', 'description', 'date', 'time', 'duration', 
                'location', 'sportType', 'maxPlayers', 'applicants', 'participants', 'followers')


class EventSerializer(serializers.ModelSerializer):
    body = EventBodySerializer()
    class Meta:
        model = Event  
        fields = ('context', 'id', 'created', 'owner', 'type', 'body')

    def validate(self, data): 
        #TODO: Extend validation checks for different scenarios
        if self.context.get('method') == "patch":
            if 'duration' in data and len(data['duration']) != 5: 
                raise serializers.ValidationError({'duration': "Given format is not correct!"})
        if self.context.get('method') == "post":
            response = {}
            required_fields = ['title', 'location', 'maxPlayers', 'date', 'time', 'duration']
            for field in required_fields:
                if field not in data:
                    response[field] = "This field is required."
            if response:
                raise serializers.ValidationError(response)
            return data
        else: return data

    def to_internal_value(self, data):
        return data

    def to_representation(self, instance):
        ret = super().to_representation(instance)
        ret['@context'] = ret.pop('context')
        ret['creator'] = User.objects.filter(pk=ret['owner']).first().username
        ret.pop('owner') 
        myorder = ['@context', 'id', 'creator', 'created', 'type', 'body']
        ordered = OrderedDict((k, ret[k]) for k in myorder)
        return ordered

    def create(self, validated_data):
        body = EventBody.objects.create(**validated_data)
        request = self.context.get('request')
        owner = request.user
        event = Event.objects.create(owner=owner, body=body) 
        return event 

    def update(self, instance, validated_data):
        regular_fields = ['title', 'description', 'date', 'time', 'duration', 'location', 'maxPlayers']
        list_fields = ['applicants', 'participants', 'followers']
        body = instance.body
        for field in regular_fields:
            if field in validated_data:
                setattr(body, field, validated_data[field])
        
        for field in list_fields:
            if field in validated_data:
                if 'add' in validated_data[field]:
                    for username in validated_data[field]['add']:
                        user = User.objects.get(username=username)
                        attribute = getattr(body, field)
                        attribute.add(user)
                if 'remove' in validated_data[field]:
                    for username in validated_data[field]['remove']:
                        user = User.objects.get(username=username)
                        attribute = getattr(body, field)
                        attribute.remove(user)

        return instance

    






