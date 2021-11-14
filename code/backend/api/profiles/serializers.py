from rest_framework import serializers

from api.authentication.models import User 

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'username', 'password', 'email', 'first_name', 'last_name', 
                  'age', 'about', 'location', 'going', 'applied', 'following']
        extra_kwargs = {'password': {"write_only": True}}
