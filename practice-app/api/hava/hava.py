import requests
from django.shortcuts import render

# Create your views here.

def hava_api(request): 

    url = 'http://api.openweathermap.org/data/2.5/weather?q={}&appid=70e1a9e29891d1988a4d585410a4a618'
    city = 'Istanbul'
    r = requests.get(url.format(city)).json()
    
    city_weather = {
        'city' : city,
        'temperature' : round( r['main']['temp'] -273,1) ,
        'description' : r['weather'][0]['description'],
        'icon': r['weather'][0]['icon'],
    } 


    print(city_weather) 

 

    context = {'city_weather' : city_weather} 

    return  render(request,'hava.html ',context)