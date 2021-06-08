"""
Created on 30.05.2021

This api gets the latest covid19 data, shows the organized and sorted data.
Users also could search according to country code

    'GET':
        Returns the html page for the case reports all over the world
    'POST':
        Using the country code information , provided by the user, it connects to the CovidPy API,
        to take the country data. Retrieves the data and passes it to the Django template "covid_country_report.html"
        where the data is processed and displayed to the user.

        JSON Format : { 'countrycode': "", string, identifies the country code user search for it


@author: Yiğit SARIOĞLU
"""

from django.shortcuts import render
import COVID19Py
import pycountry
from rest_framework.response import Response
from .form_search import SearchForm
from django.http import HttpResponse



#This method returns the data of the all over the world
#Also users could search specific country
def covid_api (request):


    covid19 = COVID19Py.COVID19()


    if request.method == 'GET':
        form=SearchForm()       #initialize a form object


        latest = covid19.getLatest()
        confirmed= latest["confirmed"]
        death = latest["deaths"]
        recover=latest["recovered"]

        deathranks = covid19.getLocations(rank_by='deaths')
        confirmedranks = covid19.getLocations(rank_by='confirmed')
        list1=[]
        for x in range(20) :
            list1.append(deathranks[x]["country"])

        list2=[]
        for x in range(20) :
            list2.append(confirmedranks[x]["country"])

        return render(request, 'covid_reports.html', {'confirm' : confirmed, 'recovery' : recover,
                                                      'death' : death, 'sform':form, "deathrank":list1, "confirmrank":list2 })

    elif request.method == 'POST':

        form = SearchForm(request.POST)
        if form.is_valid():
            country = form.cleaned_data.get('country')
            if len(country) != 2:
                return HttpResponse("<h1>Not Valid Country Code..Code length should be 2 .Please write valid code</h1>")

            elif pycountry.countries.get(alpha_2=country):
                return covid_country_api(request, country)
            else:
                return HttpResponse("<h1>Not Valid Country Code..Your Country Code is not in the table. Please look the country table. Then write a valid code</h1>")

        else:
            return HttpResponse("<h1>Not valid form request </h1>")

    else:

        return  HttpResponse("<h1>Not valid request </h1>")






#This method returns selected country covid19 data(confirmed,death,recover)
# Works with GET call.
def covid_country_api (request,countrycode):

    covid19 = COVID19Py.COVID19()

    if request.method == 'GET':

        if len(countrycode) != 2:
            return HttpResponse("<h1>Not Valid Country Code..Code length should be 2 .Please write valid code</h1>")

        elif pycountry.countries.get(alpha_2=countrycode):

            locationdata = covid19.getLocationByCountryCode(countrycode)
            countryname = locationdata[0]["country"]
            confirmed = locationdata[0]["latest"]["confirmed"]
            deaths = locationdata[0]["latest"]["deaths"]
            recovered = locationdata[0]["latest"]["recovered"]
            updatetime = locationdata[0]["last_updated"]

            return render(request, 'covid_country_report.html',
                      {'cname': countryname, 'confirm': confirmed, 'death': deaths, 'recover': recovered,
                       'time': updatetime})
        else:
            return HttpResponse(
                "<h1>Not Valid Country Code..Your Country Code is not in the table. Please look the country table. Then write a valid code</h1>")



    elif request.method == 'POST':

        locationdata = covid19.getLocationByCountryCode(countrycode)
        countryname = locationdata[0]["country"]
        confirmed = locationdata[0]["latest"]["confirmed"]
        deaths = locationdata[0]["latest"]["deaths"]
        recovered = locationdata[0]["latest"]["recovered"]
        updatetime = locationdata[0]["last_updated"]

        return render(request, 'covid_country_report.html',
                      {'cname': countryname, 'confirm': confirmed, 'death': deaths, 'recover': recovered,
                       'time': updatetime})


