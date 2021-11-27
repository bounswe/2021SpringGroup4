from django.shortcuts import render
from rest_framework.response import Response

from api.notifications.models import Notification, NotificationTEMPLATES
from .models import User 
from .serializers import RegisterUserSerializer
from rest_framework import generics, status 
from rest_framework.permissions import AllowAny

class RegisterUserView(generics.CreateAPIView):
    queryset = User.objects.all()
    permission_classes = [AllowAny]
    serializer_class = RegisterUserSerializer

    def post(self, request, *args, **kwargs):
        return_val = self.create(request, *args, **kwargs)
        if return_val.status_code == status.HTTP_201_CREATED:
            Notification.objects.create(recipient = User.objects.get(username=return_val.data['username']), body = NotificationTEMPLATES.greeting)
            return return_val
        return Response(status.HTTP_400_BAD_REQUEST)
