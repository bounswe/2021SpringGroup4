"""
Created on June 4th, 2021

This script handles the GET requests to the formula1 API endpoint http://localhost:8000/api/formula1/ ,
                and the GET and POST requests to the formula1 API endpoint http://localhost:8000/api/formula1/driver_info/

    'GET' - endpoint http://localhost:8000/api/formula1/:
        Returns the html for the current formula 1 driver standings and a search option of information about a driver from the upper table.
    'GET' - endpoint http://localhost:8000/api/formula1/driver_info/:
        Returns the html of the results page without a data, so shows a message to user and a link to redirect the user to standings page.
    'POST' - endpoint http://localhost:8000/api/formula1/driver_info/:
        Returns the html for the information about the driver that is provided by the user. 

@author: Ece Dilara Aslan
"""

from django.shortcuts import render
import requests

# Returns the needed part of the API
def main():
    response = requests.get("http://ergast.com/api/f1/current/driverStandings.json")
    data = response.json()['MRData']['StandingsTable']['StandingsLists'][0]
    return data

# Creates a list of lists which consists of the ranks and corresponding informations 
                                                        # such as driver name, constructor name, 
                                                        # points and wins
def formula1_api(request):
    data = main()
    if request.method == 'GET':
        table = []
        for i in range(len(data['DriverStandings'])):
            table.append([i+1, data['DriverStandings'][i]['Driver']['givenName']+" "+data['DriverStandings'][i]['Driver']['familyName'],
                        data['DriverStandings'][i]['Constructors'][0]['name'],
                        data['DriverStandings'][i]['points'],
                        data['DriverStandings'][i]['wins']])
        return render(request, "formula1_standings.html", {'year':data['season'], 'table':table})

def driver_info_api(request):
    data = main()
    table = []
    # Sends the information of the page is requested without a data
    if request.method == 'GET':
        return render(request, "driver_information.html", {'redirect':True})
    # Sends the information of the driver provided by the user
    # There can be three cases: no driver name is provided, 
                              # a driver which is not in the table is provided (invalid driver name),
                              # a valid driver name is provided
    elif request.method == 'POST':
        driver_name = request.data['driver_name']
        d_name = driver_name.lower().split() # name, surname or name and surname is accepted, not case-sensitive
        search_key = ""
        no_result = False
        if len(d_name) == 2:
            for i in range(len(data['DriverStandings'])):
                if d_name[0] == data['DriverStandings'][i]['Driver']['givenName'].lower() and d_name[1] == data['DriverStandings'][i]['Driver']['familyName'].lower():
                    temp = []
                    temp.append(data['DriverStandings'][i]['Driver']['givenName']+" "+data['DriverStandings'][i]['Driver']['familyName'])
                    temp.append(data['DriverStandings'][i]['Driver']['permanentNumber'])
                    temp.append(data['DriverStandings'][i]['Driver']['nationality'])
                    birth_date = data['DriverStandings'][i]['Driver']['dateOfBirth'].split('-')
                    temp.append(birth_date[2]+"/"+birth_date[1]+"/"+birth_date[0])
                    temp.append(data['DriverStandings'][i]['Driver']['url'])
                    table.append(temp)
                    search_key = temp[0]
        elif len(d_name) == 1:
            for i in range(len(data['DriverStandings'])):
                if d_name[0] == data['DriverStandings'][i]['Driver']['givenName'].lower() or d_name[0] == data['DriverStandings'][i]['Driver']['familyName'].lower():
                    temp = []
                    temp.append(data['DriverStandings'][i]['Driver']['givenName']+" "+data['DriverStandings'][i]['Driver']['familyName'])
                    temp.append(data['DriverStandings'][i]['Driver']['permanentNumber'])
                    temp.append(data['DriverStandings'][i]['Driver']['nationality'])
                    birth_date = data['DriverStandings'][i]['Driver']['dateOfBirth'].split('-')
                    temp.append(birth_date[2]+"/"+birth_date[1]+"/"+birth_date[0])
                    temp.append(data['DriverStandings'][i]['Driver']['url'])
                    table.append(temp)
                    search_key = d_name[0][0].upper() + d_name[0][1:]
                    
        if search_key == "":
            no_result = True
            for i in range(len(d_name)):
                search_key = d_name[i][0].upper() + d_name[i][1:]+ " "
            
        return render(request, "driver_information.html", {'redirect':False, 'no_result':no_result, 'search_key':search_key, 'table':table})