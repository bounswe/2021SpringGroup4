from django.http.response import JsonResponse
from django.shortcuts import render

from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import UserSerializer
from api.register.register import register_api
from api.random_article.main import ra_api

from .models import User

@api_view(['GET'])
def apiOverview(request):
    urls = {
        'Login' : '/login/',
        'Register' : '/register/',
        'Random article' : '/random_article/'
    }
    return Response(urls)

@api_view(['GET', 'POST'])
def register(request):
    return register_api(request)

@api_view(['GET'])
def rand_article(request):
    return ra_api(request)

