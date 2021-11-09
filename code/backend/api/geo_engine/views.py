from rest_framework import generics
from .models import Location
from rest_framework.permissions import AllowAny
from rest_framework import views
from django.contrib.gis.geos import Point
from geopy.geocoders import Nominatim
from rest_framework.response import Response
from django.contrib.gis.geos import GEOSGeometry
from django.contrib.gis.measure import D

from .serializers import LocationSerializer

geolocator = Nominatim(user_agent="location")


class ListCreateGenericViews(generics.ListCreateAPIView):
    permission_classes = [AllowAny]
    queryset = Location.objects.all()
    serializer_class = LocationSerializer

    def perform_create(self, serializer):
        address = serializer.initial_data["address"]
        g = geolocator.geocode(address)
        lat = g.latitude
        lng = g.longitude
        pnt = Point(lng, lat)
        print(pnt)
        serializer.save(location=pnt)


class LocationUpdateRetreiveView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Location.objects.all()
    serializer_class = LocationSerializer

    def perform_update(self, serializer):
        address = serializer.initial_data["address"]
        g = geolocator.geocode(address)
        lat = g.latitude
        lng = g.longitude
        pnt = Point(lng, lat)
        serializer.save(location=pnt)

class LocationFiltering(views.APIView):
    permission_classes = [AllowAny]
    def get(self, request, pk, format=None):
        locations = Location.objects.get(pk=pk)
        location = locations.location
        dist = request.data.get('dist')
        qs = Location.objects.filter(location__distance_lt=(location, int(dist) / 29000 * 360))
        serializer = LocationSerializer(qs, many=True)
        return Response(serializer.data)