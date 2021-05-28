"""
Created on May 23rd, 2021

This script handles the GET and POST requests to the register API endpoint http://localhost:8000/api/places/

    'GET':
        Returns the html for the search form.
    'POST':
        Using the location information provided by the user, first connects to the Google's Geocode API
        to transform this location in text format into coordinates. Afterwards, using these coordinates 
        and the keywords provided, retrieves nearby information of nearby locations and passes it to the 
        Django template 'search_places.html' where the data is processed and displayed to the user. 

        JSON Format : { 'location': "",                 string, identifies the location
                        'keyword': "",                 string, contains relevant keywords about what type of place to search for

@author: Tolga Kerimoğlu
"""

import googlemaps
import copy, hashlib, random, json
from django.shortcuts import render
from rest_framework.response import Response
from .forms import SearchPlacesForm


def places_api(request):
    """
    Process the GET and POST requests sent to the places API.

    This function processes the GET and POST requests seperately. Returns the search page for a GET request. For a POST request,
    it first connects to the Google's Geocode API to transform this location in text format into coordinates. Afterwards, using these coordinates 
    and the keywords provided, retrieves nearby information of nearby locations and passes it to the Django template 
    'search_places.html' where the data is processed and displayed to the user.

    Arguments:
    request (HttpRequest): django.http.HttpRequest object representing the incoming request

    Returns(for POST requests):
    response (Response): an HttpResponse along with a rendered html file 
    """
    if request.method == 'GET':
        form = SearchPlacesForm()
        return render(request, 'search_places.html', { 'form': form})

    if request.method == 'POST':
        location, keyword = request.data['location'], request.data['keyword']
        gmaps = googlemaps.Client(key='AIzaSyBTjZQUnMQtaGDI_M_6Zrv0tHTh2sY767c')
        loc = gmaps.geocode(location)[0]['geometry']['location']
        address = gmaps.geocode(location)[0]['formatted_address']
        lat, lng = int(loc['lat']), int(loc['lng'])
        search = gmaps.places_nearby(location=(lat, lng), rank_by='distance', keyword=keyword)
        return render(request, 'search_places.html', search, status=200)

        



