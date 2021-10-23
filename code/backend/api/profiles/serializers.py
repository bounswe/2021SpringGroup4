from api.authentication.models import User 
from rest_framework import serializers

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'username', 'password', 'email', 'first_name', 'last_name', 
                'age', 'about', 'location', 'going_to', 'applied_to', 'following']