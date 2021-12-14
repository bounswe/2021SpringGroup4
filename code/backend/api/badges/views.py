from django.shortcuts import render
from rest_framework import views  
from rest_framework import generics
from .models import Badge
from .serializers import BadgeSerializer
from rest_framework.response import Response
from rest_framework import status
from api.profiles.permissions import IsSelfOrReadOnly
from rest_framework.exceptions import APIException
from rest_framework.permissions import AllowAny, IsAuthenticatedOrReadOnly
from django.db.models import Q
from activity_handler.handlers import event_activity_handler
from api.authentication.models import User
from api.events.models import Event

class BadgeCreateView(views.APIView):
    permission_classes = [IsSelfOrReadOnly]
    def post(self, request, format=None):
        giver = request.user 
        owner = User.objects.get(username=request.data.get('owner'))
        event = Event.objects.get(id=request.data.get('event'))
        Badge.objects.create(event=event, giver=giver, owner=owner, type=request.data.get('type'))
        return Response({"response": "Badge granted successfully!"}, status=status.HTTP_201_CREATED)
