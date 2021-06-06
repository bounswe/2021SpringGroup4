"""
Created on May 23rd, 2021

This script handles the GET and POST requests to the user_profile API endpoint http://localhost:8000/api/search_user/

    'GET':
        Returns an html page including a search bar to search a user by their username.
    'POST':
        Checks if such a user exists. 
        Returns a user profile page if so, and an error response if not.

        Use the following JSON format to issue POST requests to this endpoint
        JSON Format : { 'input': ""  string, the username you want to search }

@author: Irfan Bozkurt
"""
import copy, hashlib, random, json
from django.http.response import HttpResponse
from django.shortcuts import render
from rest_framework import serializers
from rest_framework.response import Response
from django.template import RequestContext

from ..serializers import UserSerializer
from ..models import User



# First implement a search bar to retreive input from the API user.
# Its function will only be transmitting the search input from front-end to back-end, 
# so we're using Django forms.
from django import forms

class SearchBar(forms.Form):
    input = forms.CharField(label='input', max_length=30)



"""
    Process the GET and POST requests sent to the search_user API.

    This function processes the GET and POST requests seperately. Returns a search bar for a GET request. 
    For a POST request, it checks if the input is null. If not, checks if that user exists in database. If so, 
    it returns profile information regarding that user.

        Arguments:
    request (HttpRequest): django.http.HttpRequest object representing the incoming request

        Returns(for POST requests):
    response (Response): rest_framework.Response object representing the outgoing response           , OR  
    HttpResponse (request): django.http.HttpResponse object representing the outgoing response
"""
def search_user_api(request):

    if request.method == 'GET':
        
        # Insantiate a search bar, defined in bar.py
        bar = SearchBar() # Dynamic link for 'bar'               

        # Render the HTML page, using the template in templates folder under the root directory
        # The form is accesible within the HTML using jinga2 syntax                      
        return render(request, 'search_user.html', { 'bar': bar }) 

    elif request.method == 'POST':

        response = Response() # Create a rest_framework.Response object
        response['Content-type'] = 'application/json' # Set it up as a json response
        searchbar = request.data # We used a SearchBar object, and now retrieving it
                                 # to extract search input.
        # Check what is searched
        input = searchbar.get('input')

        # In case of empty input
        if len(input) == 0:
            response.status_code = 400
            response.data = { 'error': 'You must enter at least one character.'}
            return response 
        
        # TODO
        # Line below can well be changed with a well-designed search algorithm.
        # For now, as we filter w.r.t the username, the list named "result" will contain
        # 1 element only, which is supposedly the user we search for.
        result = User.objects.filter(username = input)

        # If user not found:
        if len(result) == 0:
            response.status_code = 400
            response.data = { 'error': 'User not found.'}
            return response     

        user = result[0]
        # Now we have the user. Determine the empty fields and provide
        # a dictionary to render along with the profile page.
        userdict = user.__dict__

        if not user.description:
            userdict['description']="No description provided."
        if not user.phone:
            userdict['phone']="No phone number provided."
        if not user.age:
            userdict['age']="No age provided."
        if not user.location:
            userdict['location']="No location provided."
        # If the user uploaded a pp, retrieve its url:
        if user.profile_picture:                
            userdict['profile_picture'] = str(user.profile_picture.url)
        # If the user hasn't provided a pp yet, use the UI Avatars API to 
        # retreive a rounded picture with the user's initials on it:
        else:
            name = str(user.fullname).replace(" ","+")  # Format in a way that external API desires.
            size = 128
            url = "https://ui-avatars.com/api/?rounded=true&name={}&size={}&bold=true"
            
            # Put this URL to the dictionary to use in the <img> tag
            userdict['profile_picture'] = url.format(name, size)

        return render(request, 'search_result.html', {'userdict': userdict})    
