from django.shortcuts import render
from rest_framework import views  
from rest_framework import generics
from .serializers import EventSerializer
from .models import Event 
from api.authentication.models import User 
from rest_framework.response import Response
from rest_framework import status
from .permissions import IsOwnerOrReadOnly
from rest_framework.exceptions import APIException

class EventNotFoundException(APIException):
    status_code = 404
    default_detail = "Event with the given ID does not exist. Please provide a valid ID."
    default_code = "event_not_found"

class EventListCreateView(generics.ListCreateAPIView):
    queryset = Event.objects.all()
    serializer_class = EventSerializer

class EventRetrieveUpdateDestroyView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = [IsOwnerOrReadOnly]
    queryset = Event.objects.all()
    serializer_class = EventSerializer

class EventAddRemoveView(views.APIView):
    permission_classes = [IsOwnerOrReadOnly]

    def patch(self, request, pk, format=None):
        try: 
            event = Event.objects.get(pk=pk)
        except:
            raise EventNotFoundException 

        #TODO Handle checks for whether the given user exists.
        if 'applicant' in request.data:
            applicant = User.objects.get(username=request.data['applicant'])
            event.applicants.add(applicant)
        if 'follower' in request.data:
            follower = User.objects.get(username=request.data['follower'])
            event.followers.add(follower)
        if 'participant' in request.data:
            participant = User.objects.get(username=request.data['participant'])
            event.participants.add(participant)
        event.save()
        serializer = EventSerializer(event)
        return Response(serializer.data)

    def delete(self, request, pk, format=None):
        try: 
            event = Event.objects.get(pk=pk)
        except:
            raise EventNotFoundException

        if 'applicant' in request.data:
            applicant = User.objects.get(username=request.data['applicant'])
            event.applicants.remove(applicant)
        if 'follower' in request.data:
            follower = User.objects.get(username=request.data['follower'])
            event.followers.remove(follower)
        if 'participant' in request.data:
            participant = User.objects.get(username=request.data['participant'])
            event.participants.remove(participant)
        event.save()
        serializer = EventSerializer(event)
        return Response(serializer.data)
        
    

        

