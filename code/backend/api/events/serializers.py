from rest_framework import serializers

from activity_handler.handlers import event_activity_handler
from .models import Event, EventBody, Comment
from api.authentication.models import User
from rest_framework.response import Response
from collections import OrderedDict


class CommentSerializer(serializers.ModelSerializer):
    owner = serializers.SlugRelatedField(read_only=True, slug_field="username")
    class Meta:
        model = Comment 
        fields = ('id', 'owner', 'parent', 'body')

    def create(self, validated_data):
        request = self.context.get('request')
        owner = request.user
        comment = Comment.objects.create(owner=owner, **validated_data)
        event_activity_handler(type="Comment", actor=owner, object=comment.parent.parent) 
        return comment

class EventBodySerializer(serializers.ModelSerializer):
    applicants = serializers.SlugRelatedField(many=True, read_only=True, slug_field="username")
    participants = serializers.SlugRelatedField(many=True, read_only=True, slug_field="username")
    comments = CommentSerializer(many=True)
    class Meta:
        model = EventBody
        fields = ('title', 'description', 'date', 'time', 'duration', 
                'location', 'sportType', 'maxPlayers', 'applicants', 'participants', 'comments', 'skill_level', 'lat', 'long')


class EventSerializer(serializers.ModelSerializer):
    body = EventBodySerializer()
    class Meta:
        model = Event  
        fields = ('context', 'id', 'created', 'owner', 'type', 'body')

    def validate(self, data): 
        #TODO: Extend validation checks for different scenarios
        if self.context.get('request').method == "POST":
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
        event_activity_handler(type="Create", actor=owner, object=event)
        return event 

    def update(self, instance, validated_data):
        regular_fields = ['title', 'description', 'date', 'time', 'duration', 'location', 'maxPlayers']
        list_fields = ['applicants', 'participants']
        body = instance.body
        update = False
        for field in regular_fields:
            if field in validated_data:
                update = True
                setattr(body, field, validated_data[field])
                body.save()
        if update:
            request = self.context.get('request')
            event_activity_handler(type="Update", actor=request.user, object=instance)
        for field in list_fields:
            if field in validated_data:
                if 'add' in validated_data[field]:
                    for username in validated_data[field]['add']:
                        user = User.objects.get(username=username)
                        if field == "applicants":
                            event_activity_handler(type="Apply", actor=user, object=instance)
                        if field == "participants":
                            event_activity_handler(type="Join", actor=user, object=instance)
                        attribute = getattr(body, field)
                        attribute.add(user)
                if 'remove' in validated_data[field]:
                    for username in validated_data[field]['remove']:
                        user = User.objects.get(username=username)
                        attribute = getattr(body, field)
                        attribute.remove(user)
                    
        if 'comment' in validated_data:
            if 'add' in validated_data['comment']:
                cbody = validated_data['comment']['add']
                attribute = getattr(body, 'comments')
                comment = Comment.objects.create(body=cbody, owner=self.context.get('request').user, 
                                                parent=body)
                attribute.add(comment)
            if 'remove' in validated_data['comment']:
                pk = validated_data['comment']['remove']
                attribute = getattr(body, 'comments')
                comment = Comment.objects.get(pk=pk)
                comment.delete()

        return instance

    






