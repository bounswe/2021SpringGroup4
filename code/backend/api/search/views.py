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
from .utils import distance, dist, loc

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

class EventSearch(views.APIView):
    permission_classes = [AllowAny]
    def post(self, request, *args, **kwargs):
        if kwargs.get('field') == 'location':
            location = request.data.get('location', None) 
            filter_dist = request.data.get('dist', None)
            lat, lon = loc(location)
            ids = []
            for event in EventBody.objects.all().only('id', 'location'):
                id, event_loc = event.id, event.location
                try:
                    lat2, lon2 = loc(event_loc)
                except IndexError:
                    continue

                dist2src = distance(lat, lat2, lon, lon2) 
                if dist2src < int(filter_dist):
                    ids.append(id)
            return_events = []
            qs = EventBody.objects.filter(Q(id__in=ids))
            for event in qs:
                data = {}
                data['owner'] = event.parent.owner.username 
                data['id'] = event.id 
                data['title'] = event.title 
                data['location'] = event.location 
                return_events.append(data)
            return Response(status=200, data=return_events, content_type='json')