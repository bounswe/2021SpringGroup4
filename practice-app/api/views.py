from django.http.response import JsonResponse
from django.shortcuts import render

from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import UserSerializer
from .serializers import PostSerializer
from api.register.register import register_api
from api.random_article.main import ra_api
from api.eq_post.main import eq_post_api

from .models import User
from .models import Post

@api_view(['GET'])
def apiOverview(request):
    urls = {
        'Login' : '/login/',
        'Register' : '/register/',
        'Random article' : '/random_article/',
        'Equipment post' : '/eq_post/'
    }
    return Response(urls)

@api_view(['GET', 'POST'])
def register(request):
    return register_api(request)

@api_view(['GET'])
def rand_article(request):
    return ra_api(request)

@api_view(['GET', 'POST'])
def eq_post(request):
    return eq_post_api(request)