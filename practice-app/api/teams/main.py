"""
Created on 01.06.2021

This api gets an NBA team name and gives information about the team.

@author: Berkay Gümüş
"""
from django.shortcuts import render
from .choice_team import ChoiceTeam
from rest_framework.response import Response
from nba_api.stats.static import teams

def team_api(request):
    if request.method == 'GET':

        context = {}
        context['form'] = ChoiceTeam(teams=teams.get_teams())
        return render(request=request, template_name='team.html', context=context)

def list_team_api(request, team_code=None):
    if request.method == 'GET':
        response = Response()    # Create a rest_framework.Response object
        response['Content-type'] = 'application/json'   # Set it up as a json response
        team_found = teams.find_team_by_abbreviation(abbreviation=team_code)
        if team_found is None:
            response.status_code = 400
            response.data = { 'NOT VALID ABBREVIATION' }
            return response
        else:
            return  render(request, 'list_team.html', team_found)
        
    elif request.method == 'POST':
        abbreviation:str = request.data.get('team_field')
        team_found = teams.find_team_by_abbreviation(abbreviation=abbreviation)

        return  render(request, 'list_team.html', team_found)


        


        