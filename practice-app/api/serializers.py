from rest_framework import serializers
from .models import User
from .models import Post
from .models import EventPost

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = '__all__'

class PostSerializer(serializers.ModelSerializer):
    class Meta:
        model = Post
        fields = '__all__'

class EventPostSerializer(serializers.ModelSerializer):
    class Meta:
        model = EventPost
        fields = '__all__'