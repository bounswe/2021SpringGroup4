from django.http.response import JsonResponse
from django.shortcuts import render

from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import UserSerializer
from api.register.register import register_api

from .models import User

@api_view(['GET'])
def apiOverview(request):
    urls = {
        'Login' : '/login/',
        'Register' : '/register/'
    }
    return Response(urls)

@api_view(['GET', 'POST'])
def register(request):
    return register_api(request)

