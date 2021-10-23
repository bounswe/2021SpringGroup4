from django.shortcuts import render
from .models import User 
from .serializers import RegisterUserSerializer
from rest_framework import generics 
from rest_framework.permissions import AllowAny

class RegisterUserView(generics.CreateAPIView):
    queryset = User.objects.all()
    permission_classes = [AllowAny]
    serializer_class = RegisterUserSerializer
