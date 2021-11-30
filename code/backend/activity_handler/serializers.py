from rest_framework import serializers
from rest_framework.serializers import ModelSerializer
from api.authentication.models import User
from api.events.models import Event, EventBody
from .models import EventActivity
from api.events.serializers import EventBodySerializer
from collections import OrderedDict

class ActorSerializer(ModelSerializer):
    type = serializers.ReadOnlyField(default="User")
    name = serializers.SlugRelatedField(read_only=True, slug_field="first_name")
    class Meta:
        model = User
        fields = ('type', 'id', 'name', 'username')

class EventObjectSerializer(ModelSerializer):
    type = serializers.ReadOnlyField(default="Event")
    name = serializers.SlugRelatedField(read_only=True, slug_field="title")
    owner = serializers.SlugRelatedField(read_only=True, slug_field="username")
    content = EventBodySerializer(source="body")

    class Meta:
        model = Event
        fields = ('type', 'name', 'id', 'created', 'owner', 'content')

class EventActivitySerializer(ModelSerializer):
    actor = ActorSerializer()
    object = EventObjectSerializer()

    def to_representation(self, instance):
        ret = super().to_representation(instance)
        ret['@context'] = ret.pop('context')
        myorder = ['@context', 'summary', 'type', 'performed_at', 'actor', 'object']
        ordered = OrderedDict((k, ret[k]) for k in myorder)
        return ordered

    class Meta:
        model = EventActivity
        fields = ('context', 'summary', 'type', 'performed_at', 'actor', 'object')
