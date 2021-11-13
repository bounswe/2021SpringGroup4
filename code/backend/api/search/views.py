from django.shortcuts import render
from api.events.serializers import EventSerializer, EventBodySerializer, CommentSerializer
from api.events.models import Event, EventBody, Comment
from api.authentication.models import User 
from api.profiles.serializers import UserSerializer
from rest_framework import generics 
from rest_framework import views
from rest_framework.permissions import AllowAny
from django.db.models import Q
from rest_framework.response import Response
from rest_framework import status

class UserSearch(views.APIView):
    def get(self, request, *args, **kwargs):
        if kwargs.get('field') == 'username':
            qs = User.objects.filter(Q(username__contains=kwargs.get('value')))
            serializer = UserSerializer(qs, many=True)
            return Response(status=status.HTTP_200_OK, data=serializer.data)  
        if kwargs.get('field') == 'name':
            qs = User.objects.filter(Q(first_name__contains=kwargs.get('value')))
            serializer = UserSerializer(qs, many=True)
            return Response(status=status.HTTP_200_OK, data=serializer.data)  
