"""
Created on 30.05.2021

This script handles the GET and POST requests to the covid19 API endpoint http://localhost:8000/api/covid19/
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
    covid19 = COVID19Py.COVID19()    #create a new instance of covid19

    if request.method == 'GET':
        form=SearchForm()       #initialize a form object

        latest = covid19.getLatest()   #Getting latest amount of total confirmed cases, deaths, and recoveries
        confirmed= latest["confirmed"]  #latest amount of total confirmed case all over the world
        death = latest["deaths"]        #latest amount of total deaths all over the world
        recover=latest["recovered"]     #latest amount of total recover all over the world

        deathranks = covid19.getLocations(rank_by='deaths')           #  rank the results deaths 
        confirmedranks = covid19.getLocations(rank_by='confirmed')    #  rank the results confirmed
        #Here list1 takes the name of the top 20 countries according to death
        list1=[]
        for x in range(20) :
            list1.append(deathranks[x]["country"])
            
        #Here list2 takes the name of the top 20 countries according to confirmed case
        list2=[]
        for x in range(20) :
            list2.append(confirmedranks[x]["country"])
        
        # Returns the search form for the user, and the latest amount of total confirmed cases, deaths, and recoveries information,
        # Also returns death rankings and confirmed case rankings of countries 
        return render(request, 'covid_reports.html', {'confirm' : confirmed, 'recovery' : recover,
                                                      'death' : death, 'sform':form, "deathrank":list1, "confirmrank":list2 })

    elif request.method == 'POST':
        form = SearchForm(request.POST)  #initialize a form object
        if form.is_valid():
            country = form.cleaned_data.get('country')   #gets the country code from the form
            if len(country) != 2:                #if the country code length is not equal to 2. ıt is not a valid countrycode. so it returns HttpResponse
                return HttpResponse("<h1>Not Valid Country Code..Code length should be 2 .Please write valid code</h1>")
            
            #Using pycountry package, specific countries can be looked up by their various codes 
            elif pycountry.countries.get(alpha_2=country):    #checks the country code, with using pycountry
                return covid_country_api(request, country)  # if it is valid, it  calls the covid_country_api method to show the specific country data
            else:  #if that country code is not valid, HttpResponse sended
                return HttpResponse("<h1>Not Valid Country Code..Your Country Code is not in the table. Please look the country table. Then write a valid code</h1>")

        else:
            return HttpResponse("<h1>Not valid form request </h1>")

    else:

        return  HttpResponse("<h1>Not valid request </h1>")






#This method returns selected country covid19 data(confirmed,death,recovered)
def covid_country_api (request,countrycode):

    covid19 = COVID19Py.COVID19()   #Create a new instance of covid19
    # GET method : Users could search the country using :  /covid19/countrycode  
    # for example: /covid19/TR  calls the "GET" and returns the turkey covid data
    if request.method == 'GET':  #If GET method is called

        if len(countrycode) != 2:    #a valid country code should be 2.this statement check this condition
            return HttpResponse("<h1>Not Valid Country Code..Code length should be 2 .Please write valid code</h1>")

        elif pycountry.countries.get(alpha_2=countrycode):  #checks the country code, with using pycountry 

            locationdata = covid19.getLocationByCountryCode(countrycode)  #get the data according to specific country code
            countryname = locationdata[0]["country"]                      # gets the country name data
            confirmed = locationdata[0]["latest"]["confirmed"]            # gets the country confirmed cases
            deaths = locationdata[0]["latest"]["deaths"]                 # gets the country death data
            recovered = locationdata[0]["latest"]["recovered"]           # gets the country recovered  data
            updatetime = locationdata[0]["last_updated"]                  # gets the update time
            
            #Returns the confirmed cases,deaths ,recovery , update time of data and country name
            return render(request, 'covid_country_report.html',
                      {'cname': countryname, 'confirm': confirmed, 'death': deaths, 'recover': recovered,
                       'time': updatetime})
        else:  #if that country code is not valid, HttpResponse sended
            return HttpResponse(
                "<h1>Not Valid Country Code..Your Country Code is not in the table. Please look the country table. Then write a valid code</h1>")


     # POST method : called when user post a request
    elif request.method == 'POST':

        locationdata = covid19.getLocationByCountryCode(countrycode)  
        countryname = locationdata[0]["country"]                      # gets the country name data
        confirmed = locationdata[0]["latest"]["confirmed"]            # gets the country confirmed cases
        deaths = locationdata[0]["latest"]["deaths"]                 # gets the country death data
        recovered = locationdata[0]["latest"]["recovered"]          # gets the country recovered  data
        updatetime = locationdata[0]["last_updated"]                 #gets the update time

        #Returns the confirmed cases,deaths ,recovery , update time of data and country name
        return render(request, 'covid_country_report.html',
                      {'cname': countryname, 'confirm': confirmed, 'death': deaths, 'recover': recovered,
                       'time': updatetime})


