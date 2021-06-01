"""
Created on 30.05.2021

This api gets the latest covid19 data, shows the organized and sorted data.
Users also could search according to country code

@author: Yiğit SARIOĞLU
"""

from django.shortcuts import render
import COVID19Py
import pycountry
from rest_framework.response import Response
from .form_search import SearchForm



covid19 = COVID19Py.COVID19()

#This method returns the data of the all world
def covid_api (request):
    if request.method == 'GET':
        form=SearchForm()

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
        response = Response()    # Create a rest_framework.Response object
        response['Content-type'] = 'application/json'   # Set it up as a json response
        data = request.data
        # Extract user information from the request
        (country) = (data.get('country') )

        if len(country) != 2:
            response.status_code = 400
            response.data = { 'NOT VALİD,  A valid country code (length =2 ) must be provided.'}
            return response

        else:
             response.data = { 'status': 'a valid countrycode entered'}
             return response

        return response






#This method returns selected country covid19 data(confirmed,death,recover)
# Works with GET call.
def covid_country_api (request,countrycode):

    locationdata = covid19.getLocationByCountryCode(countrycode)
    countryname = locationdata[0]["country"]
    confirmed = locationdata[0]["latest"] ["confirmed"]
    deaths = locationdata[0]["latest"] ["deaths"]
    recovered = locationdata[0]["latest"] ["recovered"]
    updatetime= locationdata[0]["last_updated"]

    if request.method == 'GET':
            return render(request, 'covid_country_report.html', {'cname':countryname, 'confirm' : confirmed ,'death' : deaths , 'recover': recovered, 'time': updatetime})



    elif request.method == 'POST':

            return  render(request, 'covid_country_report.html', {'cname':countryname, 'confirm' : confirmed ,'death' : deaths , 'recover': recovered, 'time': updatetime})



