from rest_framework import serializers
from .models import Event, EventBody, Comment
from api.authentication.models import User
from rest_framework.response import Response
from collections import OrderedDict

class CommentSerializer(serializers.ModelSerializer):
    owner = serializers.SlugRelatedField(read_only=True, slug_field="username")
    class Meta:
        model = Comment 
        fields = ('id', 'owner', 'parent', 'body')

class EventBodySerializer(serializers.ModelSerializer):
    applicants = serializers.SlugRelatedField(many=True, read_only=True, slug_field="username")
    participants = serializers.SlugRelatedField(many=True, read_only=True, slug_field="username")
    followers = serializers.SlugRelatedField(many=True, read_only=True, slug_field="username")
    comments = CommentSerializer(many=True)
    class Meta:
        model = EventBody
        fields = ('title', 'description', 'date', 'time', 'duration', 
                'location', 'point', 'sportType', 'maxPlayers', 'applicants', 'participants', 'followers', 'comments',)
        extra_kwargs = {"point": {"read_only": True}}

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

    






