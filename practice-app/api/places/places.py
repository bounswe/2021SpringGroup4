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
from django.shortcuts import render
from rest_framework.response import Response
from .forms import SearchPlacesForm


def places_api(request):
    """
    Process the GET and POST requests sent to the places API.

    This function processes the GET and POST requests seperately. Returns the search page for a GET request. For a POST request,
    it first connects to the Google's Geocode API to transform this location in text format into coordinates. Afterwards, using these coordinates 
    and the keywords provided, retrieves nearby information of nearby locations and passes it to the Django template 
    'search_places.html' where the data is processed and displayed to the user with address and ratings information displayed.

    Arguments:
    request (HttpRequest): django.http.HttpRequest object representing the incoming request

    Returns(for POST requests):
    response (Response): an HttpResponse along with a rendered html file 
    """
    if request.method == 'GET':
        form = SearchPlacesForm() # Initialize the search form
        return render(request, 'search_places.html', { 'form': form}) # Return the form

    if request.method == 'POST':
        location, keyword = request.data['location'], request.data['keyword'] # Store information received from the user
        gmaps = googlemaps.Client(key='AIzaSyBTjZQUnMQtaGDI_M_6Zrv0tHTh2sY767c') # Connect to the Google Maps API
        loc = gmaps.geocode(location)[0]['geometry']['location'] # Convert the entered location into coordinates using the geocode API 
        address = gmaps.geocode(location)[0]['formatted_address'] # Store the address
        lat, lng = int(loc['lat']), int(loc['lng']) # Store the latitute and longitude information
        search = gmaps.places_nearby(location=(lat, lng), rank_by='distance', keyword=keyword) # Use coordinate information to search for places nearby with given keywords
        if (location == "" or keyword == ""): # Check for empty requests
            return render(request, 'search_places.html', {'fail': True}, status=400)
        return render(request, 'search_places.html', search, status=200) # Return the response to be processed inside the html template 

        



