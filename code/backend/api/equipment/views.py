from django.shortcuts import render
from api.authentication.models import User 
from .models import EquipmentPost
from rest_framework import generics 
from .serializers import EquipmentSerializer
from api.events.permissions import IsOwnerOrReadOnly

class EquipmentPostListCreateView(generics.ListCreateAPIView):
    queryset = EquipmentPost.objects.all()
    serializer_class = EquipmentSerializer

class EquipmentPostRetrieveUpdateDestroyView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = [IsOwnerOrReadOnly]
    queryset = EquipmentPost.objects.all()
    serializer_class = EquipmentSerializer


