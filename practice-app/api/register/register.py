import copy, hashlib, random, json
from django.shortcuts import render
from rest_framework import serializers
from rest_framework.response import Response
from api.serializers import UserSerializer
from .forms import RegistrationForm

def register_api(request):
    """
    Process the GET and POST requests sent to the register API.

    This function processes the GET and POST requests seperately. Returns the registration page for a GET request. For a POST request,
    it validates the information, encrypts the valuable fields such as password and saves the user to the database and returns an HttpResponse containing
    the meta-information of the newly registered user. If any errors occur, returns a response describing what went wrong. 

    Arguments:
    request (HttpRequest): django.http.HttpRequest object representing the incoming request

    Returns(for POST requests):
    response (Response): rest_framework.Response object representing the outgoing response

    """
    if request.method == 'GET':
        form = RegistrationForm() # Insantiate a registration form, defined in forms.py
        return render(request, 'register.html', { 'form': form }) # Render the HTML page, using the template in templates folder under the root directory
                                                                  # The form is accesible within the HTML using jinga2 syntax
    
    elif request.method == 'POST':
        response = Response() # Create a rest_framework.Response object
        response['Content-type'] = 'application/json' # Set it up as a json response
        data = request.data 
        # Extract user information from the request
        (username, password, email, description, age, location) = (data.get('username'), 
                                                                data.get('password'), 
                                                                data.get('email'), 
                                                                data.get('description'), 
                                                                data.get('age'), 
                                                                data.get('location')) 

        hashed_pw = hashlib.sha256(password.encode()).hexdigest() # Hash the password
        form = copy.copy(data) # Copy the data to a new dict
        form['hashed_pw'] = hashed_pw # Insert the hashed password
        serializer = UserSerializer(data = form) # Create a serializer for the database entry of the user
        if serializer.is_valid(): # If valid, save user to the database and return a success response
            response.status_code = 201
            response.data = { 'status': 'registration SUCCESSFUL'}
            serializer.save()
        else: # If not valid, return the appropriate page
            response.data = serializer.errors
            # TODO: Instead of returning the errors in JSON format, return a proper HTML file displaying the error

    return response

