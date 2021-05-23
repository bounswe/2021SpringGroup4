import requests 
import copy, hashlib, random, json
from django.shortcuts import render
from rest_framework.response import Response
from api.serializers import UserSerializer
from .forms import RegistrationForm

def register_api(request):
    response = Response()
    response.status_code = 200              # set status to 200 as default
    response["Content-type"] = "application/json"
    response.data = {'status': 'accepted'}
    data = request.data
    

    username, password, email, description, age, location = data.get('username'),data.get('password'), data.get('email'), data.get('description'), data.get('age'), data.get('location')
    serializer = UserSerializer(data = data)
    print(data)
    if serializer.is_valid(): 
        serializer.save()
    if request.method == 'GET':
        form = RegistrationForm()
        return render(request, 'register.html', {'form': form})
    return response

