import googlemaps 
from datetime import datetime 
from math import radians, cos, sin, asin, sqrt 
import json

def distance(lat1, lat2, lon1, lon2):

    lon1 = radians(lon1)
    lon2 = radians(lon2)
    lat1 = radians(lat1)
    lat2 = radians(lat2)
      
    dlon = lon2 - lon1
    dlat = lat2 - lat1
    a = sin(dlat / 2)**2 + cos(lat1) * cos(lat2) * sin(dlon / 2)**2
 
    c = 2 * asin(sqrt(a))
    
    r = 6371
      
    return(c * r)
     

def dist(src, dest):
    gmaps = googlemaps.Client(key='AIzaSyCF6fCm1kCj66VhzhzlwYsBQriKVnn8rYk')
    geocode_source = gmaps.geocode(src)[0]
    geocode_dest = gmaps.geocode(dest)[0]
    lat1, lon1 = (geocode_source['geometry']['location']).values()
    lat2, lon2 = (geocode_dest['geometry']['location']).values()
    return distance(lat1, lat2, lon1, lon2)

def loc(location):
    gmaps = googlemaps.Client(key='AIzaSyCF6fCm1kCj66VhzhzlwYsBQriKVnn8rYk')
    geocode_source = gmaps.geocode(location)[0]  
    lat1, lon1 = (geocode_source['geometry']['location']).values()
    return lat1, lon1
