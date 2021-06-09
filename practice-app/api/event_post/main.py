
"""
 GET and POST requests to the  http://localhost:8000/api/event_post/

    'GET':
        Returns the  event creation page.
    'POST':
        Validates the necessary informations are filled and check for validity of that informations to record the event to our database if it is valid
        JSON Format : { 'eventName': "",                 string, the eventname entered by the event creator, also a primary key for db table
                        'sportType': "",                 string, the sporttype entered by the user
                        'numOfPlayers ':"",              int, the number of players wanted for that event given by the event creator
                        'description':"",                string, a description about the event created by event creator, this can be NULL
                        'date':"",                       date(year:int, month:int, day: int): , a valid date that can be selected 
                        'time':"",                       time (hour: int, min: int), a valid time that can be selected 

@author: Yağmur Selek
"""

from django.http import HttpResponse
import copy
from django.shortcuts import render

import time
import pytz
from datetime import datetime
from rest_framework.response import Response
from api.serializers import EventPostSerializer
from .forms import EventPost


def event_post_api(request):
    todayDateTime= datetime.today()
    todayDate=todayDateTime.strftime('%Y-%m-%d')
    todayTime=todayDateTime.strftime('%H.%M')


    if request.method == 'GET':
        event_post = EventPost() #Initiliaze event post form defined in event_post/forms.py
        return render(request, 'event_post.html', { 'post': event_post }) #render the event_post.html page
    
    elif request.method == 'POST':
        #create the json response object 
        response = Response() 
        response['Content-type'] = 'application/json'
        data = request.data 

        #get the data fields according to given order 
        (eventName, sportType, numOfPlayers,description,date,time) = (data.get('eventName'), data.get('sportType'), data.get('numOfPlayers'), data.get('description'),data.get('date'),data.get('time')) 
       
       #if event name field is not entered then give status code 400
        if len(eventName) == 0 :
            response.status_code = 400
            response.data = { 'eventName': 'A eventName must be provided.'}
            return response
         #if sportType field is not provided then give status code 401
        if len(sportType) == 0 :
           response.status_code = 401
           response.data = { 'sportType': 'A sportType must be provided.'}
           return response
        #if date field is provided as a past date then give status code 402
        if date < todayDate :
           response.status_code = 402
           response.data = { 'date': 'You can not enter a past date'}
           return response
        if date==todayDate and time<todayTime :
           response.status_code = 402
           response.data = { 'time': 'You can not enter a past time'}
           return response

        form = copy.copy(data)
        response.status_code = 201

        serializer = EventPostSerializer(data = form)
        if serializer.is_valid(): # If valid 
            response.status_code = 201 # return success status code 201
            
            serializer.save()  #save event to the database
        else: # If not valid
            response.status_code = 400
            response.data = serializer.errors
            # TODO: Instead of returning the errors in JSON format, return a proper HTML file displaying the error


    return HttpResponse("<h1>You have successfully created your event !</h1>")   