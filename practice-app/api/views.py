from django.http.response import JsonResponse
from django.shortcuts import render


from rest_framework.decorators import api_view
from rest_framework.response import Response
from .serializers import UserSerializer
from .serializers import PostSerializer
from api.register.register import register_api
from api.places.places import places_api
from api.random_article.main import ra_api
from api.eq_post.main import eq_post_api
from api.event_post.main import event_post_api

from api.covid_reports.main import covid_api
from api.covid_reports.main import covid_country_api
from api.covid_reports.form_search import SearchForm
from django.http import HttpResponse
import pycountry

from .models import User
from .models import Post
from .models import EventPost

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

    if(request.method=='POST'):
        form=SearchForm(request.POST)
        if form.is_valid():
            country = form.cleaned_data.get('country')
            if len(country) != 2:
                return HttpResponse("<h1>Not Valid Country Code.. Please write valid code</h1>")

            elif pycountry.countries.get(alpha_2= country)  :
                return covid_country_api(request,country)
            else:
                return HttpResponse("<h1>Not Valid Country Code.. Please write valid code</h1>")
    else :
        return covid_api(request)

@api_view(['GET'])
def covid_country(request,countrycode):
    if len(countrycode) != 2:
        return HttpResponse("<h1>Not Valid Country Code.. Please write valid code</h1>")

    elif pycountry.countries.get(alpha_2= countrycode)  :
        return covid_country_api(request,countrycode)

    else:
        return HttpResponse("<h1>Not Valid Country Code.. Please write valid code</h1>")


@api_view(['GET', 'POST'])
def eq_post(request):
    return eq_post_api(request)


@api_view(['GET', 'POST'])
def event_post(request):
    return event_post_api(request)
