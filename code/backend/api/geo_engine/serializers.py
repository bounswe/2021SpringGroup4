from .models import Location
from rest_framework import serializers


class LocationSerializer(serializers.ModelSerializer):
    class Meta:
        model = Location

        fields = ("id", "name", "address", "location")

        extra_kwargs = {"location": {"read_only": True}}