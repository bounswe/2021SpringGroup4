
import copy
from django.shortcuts import render

import time
import pytz
from datetime import datetime
from rest_framework.response import Response
from api.serializers import EventPostSerializer
from .posts import EventPost


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
        (eventName, sportType, numOfPlayers,description,date,time) = (data.get('eventName'), data.get('sportType'), data.get('numOfPlayers'), data.get('description'),data.get('date'),data.get('time')) 
        if len(eventName) == 0 :
            response.status_code = 400
            response.data = { 'eventName': 'A eventName must be provided.'}
            return response
        if len(sportType) == 0 :
           response.status_code = 401
           response.data = { 'sportType': 'A sportType must be provided.'}
           return response
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
            response.data = { 'status': 'EVENT POST SUCCESSFUL'}
            serializer.save()  #save event to the database
        else: # If not valid
            response.status_code = 400
            response.data = serializer.errors
            # TODO: Instead of returning the errors in JSON format, return a proper HTML file displaying the error


    return response