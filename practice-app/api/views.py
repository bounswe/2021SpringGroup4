from django.http.response import JsonResponse
from django.shortcuts import render
import requests   

from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import UserSerializer
from .serializers import PostSerializer
from api.register.register import register_api
from api.places.places import places_api
from api.random_article.main import ra_api
from api.eq_post.main import eq_post_api
from api.holidays.main import holidays_api
from api.hava.hava import hava_api
from api.covid_reports.main import covid_api
from api.covid_reports.main import covid_country_api
from api.covid_reports.form_search import SearchForm
from django.http import HttpResponse
import pycountry

from .models import User
from .models import Post

@api_view(['GET'])
def apiOverview(request):
    urls = {
        'Login' : '/login/',
        'Register' : '/register/',
        'Random article' : '/random_article/',
        'Equipment post' : '/eq_post/',
        'Covid19 Case Reports' : '/covid19/'
    }
    return Response(urls)

@api_view(['GET', 'POST'])    
def hava(request):
    return hava_api(request)


@api_view(['GET', 'POST'])
def register(request):
    return register_api(request)

@api_view(['GET', 'POST'])
def places(request):
    return places_api(request)

@api_view(['GET'])
def rand_article(request):
    return ra_api(request)

@api_view(['GET','POST'])
def covid19(request):
    return covid_api(request)

@api_view(['GET'])
def covid_country(request,countrycode):
    return covid_country_api(request,countrycode)

@api_view(['GET', 'POST'])
def eq_post(request):
    return eq_post_api(request)

@api_view(['GET', 'POST'])
def holidays(request):
    return holidays_api(request)
