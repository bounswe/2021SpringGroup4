"""
Created on 27.05.2021

This script handles the GET and POST requests to the eq_post API endpoint http://localhost:8000/api/eq_post/

    'GET':
        Returns the equipment posting page.
    'POST':
        Validates the information and posts the equipment into the database if valid, returns a descriptive error if not
        Use the following JSON format to issue POST requests to this endpoint
        JSON Format : { 'username': "",                 string, the username selected by the user
                        'title': "",                    string, the title selected by the user
                        'description':"",               string, a description about the user
                        'location':"",                  string, location provided by the user

@author: Salih Furkan Akkurt
"""

import copy
from django.shortcuts import render
from rest_framework import serializers
from rest_framework.response import Response
from api.serializers import PostSerializer
from .posts import EquipmentPost

def eq_post_api(request):
    if request.method == 'GET':
        eq_post = EquipmentPost()
        return render(request, 'eq_post.html', { 'post': eq_post })
    
    elif request.method == 'POST':
        response = Response()
        response['Content-type'] = 'application/json'
        data = request.data 
        (username, title, description, location) = (data.get('username'), data.get('title'), data.get('description'), data.get('location')) 
        if len(title) == 0:
            response.status_code = 400
            response.data = { 'title': 'A title must be provided.'}
            return response
        form = copy.copy(data)
        serializer = PostSerializer(data = form)
        if serializer.is_valid():
            response.status_code = 201
            response.data = { 'status': 'post SUCCESSFUL'}
            serializer.save()
        else:
            response.status_code = 400
            response.data = serializer.errors
    return response