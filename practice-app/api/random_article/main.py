"""
Created on 26.05.2021

This script gets a random sports arena article from Wikipedia.

@author: Salih Furkan Akkurt
"""
import random
from rest_framework.response import Response
from django.shortcuts import render
import requests
import json
from bs4 import BeautifulSoup
import re

def ra_api(request):
    if request.method == 'GET':
        f=open('api/random_article/arena_list.txt','r')
        a_l = f.read().split('\n')
        f.close()
        r_n = random.randrange(len(a_l))
        r_url = 'https://en.wikipedia.org/w/api.php?action=query&titles={}&prop=extracts&format=json'.format(a_l[r_n])
        req_page = requests.get(r_url)
        page_text = json.loads(req_page.text)
        page_text = list(page_text['query']['pages'].values())[0]['extract']
        soup = BeautifulSoup(page_text, 'html.parser')
        page_text = soup.get_text()
        if 'References' in page_text:
            idx = page_text.index('References')
            page_text = page_text[:idx]
        if 'See also' in page_text:
            idx = page_text.index('See also')
            page_text = page_text[:idx]
        if 'External links' in page_text:
            idx = page_text.index('External links')
            page_text = page_text[:idx]
        if page_text == '':
            response = Response()
            response['Content-type'] = 'application/json'
            response.status_code = 400
            return response
        return render(request, 'rand_wiki.html', {'rand_article' : page_text})