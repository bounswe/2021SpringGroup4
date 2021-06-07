from django.http.response import JsonResponse
from django.shortcuts import render
import requests   


from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import UserSerializer
from .serializers import PostSerializer
from .register.register import register_api
from .places.places import places_api
from .random_article.main import ra_api
from .eq_post.main import eq_post_api

from .covid_reports.main import covid_api
from .covid_reports.main import covid_country_api
from .covid_reports.form_search import SearchForm
from .search_user.search_user import search_user_api
from api.register.register import register_api
from api.places.places import places_api
from api.random_article.main import ra_api
from api.eq_post.main import eq_post_api
from api.holidays.main import holidays_api
from api.event_post.main import event_post_api
from api.hava.hava import hava_api
from api.covid_reports.main import covid_api
from api.covid_reports.main import covid_country_api
from api.covid_reports.form_search import SearchForm
from .teams.main import list_team_api
from .teams.main import team_api
from django.http import HttpResponse

from api.formula1.formula1 import formula1_api, driver_info_api

from .models import User
from .models import Post
from .models import EventPost

@api_view(['GET'])
def index(request):
    return render(request, 'index.html')

@api_view(['GET'])
def apiOverview(request):
    urls = {
        'Login' : '/login/',
        'Register' : '/register/',
        'Random article' : '/random_article/',
        'Equipment post' : '/eq_post/',
        'Covid19 Case Reports' : '/covid19/',
        'Formula 1' : '/formula1/',
        'NBA Teams Information' : '/team/',

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

@api_view(['GET', 'POST'])  
def select_team(request):
    if request.method =='POST':
        return list_team_api(request=request)
    elif request.method == 'GET':
        return team_api(request=request)

@api_view(['GET'])
def list_team(request, team_code):
    return list_team_api(request=request, team_code=team_code)

@api_view(['GET'])
def formula1(request):
    return formula1_api(request)

@api_view(['GET', 'POST'])
def eq_post(request):
    return eq_post_api(request)


@api_view(['GET', 'POST'])
def event_post(request):
    return event_post_api(request)
def search_user(request):
    return search_user_api(request)

def driver_info(request):
    return driver_info_api(request)


