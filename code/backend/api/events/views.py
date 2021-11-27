from django.shortcuts import render
from rest_framework import views  
from rest_framework import generics

from api.notifications.models import Notification, NotificationTEMPLATES
from .serializers import EventSerializer
from .models import Event, EventBody 
from api.authentication.models import User 
from rest_framework.response import Response
from rest_framework import status
from .permissions import IsOwnerOrReadOnly
from rest_framework.exceptions import APIException
from rest_framework.permissions import AllowAny, IsAuthenticatedOrReadOnly
from django.db.models import Q


class EventNotFoundException(APIException):
    status_code = 404
    default_detail = "Event with the given ID does not exist. Please provide a valid ID."
    default_code = "event_not_found"

class EventListCreateView(views.APIView):
    permission_classes = [IsAuthenticatedOrReadOnly]

    def get(self, request, format=None):
        events = Event.objects.all()
        serializer = EventSerializer(events, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = EventSerializer(data=request.data, context={"request": request})
        if serializer.is_valid():
            serializer.save()
            Notification.objects.create(recipient = request.user, body = NotificationTEMPLATES.event_created + Event.objects.last().id)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class EventDetailView(views.APIView):
    permissions_classes = [IsOwnerOrReadOnly]

    def get(self, request, pk, format=None):
        event = Event.objects.get(pk=pk)
        serializer = EventSerializer(event)
        return Response(serializer.data)

    def delete(self, request, pk):
        try: 
            event = Event.objects.get(pk=pk)
        except Event.DoesNotExist:
            return Response({"status": "Event with the given ID does not exists"}, status=status.HTTP_400_BAD_REQUEST)
        event.delete()
        return Response({"status": "Event deleted successfully."}, status=status.HTTP_204_NO_CONTENT)

    def patch(self, request, pk):
        try: 
            event = Event.objects.get(pk=pk)
        except Event.DoesNotExist:
            return Response({"status": "Event with the given ID does not exists"}, status=status.HTTP_400_BAD_REQUEST)

        serializer = EventSerializer(event, request.data, context={"request": request})
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_202_ACCEPTED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


    

        

