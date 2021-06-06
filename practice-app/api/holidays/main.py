"""
Created on Jun 5th, 2021

holidays api gets the date information by "datetime" and holiday information of the countries by google calender. Firstly
the api recieve the necessary informations by GET method in holiday_form=(day,month,year,country_code). Then,
country holiday information is taken from holidays as list. In this state, the api checks whether the input for date is
proper and if it is not the data_massage show this situtation. If the date format is correct, the program checks whether
the requestad date is holiday or not. If it is not holiday, The "date is proper" message should be showed. However, if it
is a holiday day, the program finds the first next day which is not holiday and the properr message should be showed.
In this state, the created massage is rendered with holidays.html by render method. 


@author: Mehmet Hilmi Dündar
"""


from os import abort
from django.shortcuts import render
from .holiday_form import HolidayForm
from datetime import date, datetime, timedelta
import requests
import json


def holidays_api(request):

    #gathering data
    if request.method == 'GET':
        form = HolidayForm()
        return render(request, 'holidays.html', { 'form': form})

    #showing properr massage
    if request.method == 'POST':
        #gathered information
        day_input=request.data['day']
        month_input=request.data['month']
        year_input=request.data['year']
        country_code = request.data['country_code']
        country_code_lower = country_code.lower()

        error_case=False
        error_case_message='' 

        if(day_input=='' or month_input=='' or year_input=='' or country_code==''):
            error_case_message='null element'
            error_case=True
        
        try:
            day = int(day_input)
            month = int(month_input)
            year = int(year_input)
        except:
            error_case_message='not integer'
            error_case=True    
       
        #showing error:
        if error_case:
            return render(request, 'holidays.html', {'input_error': True, 'error_message': error_case_message}, status=400) 



        #necesssery information
        countries_dict={'turkey':'turkish','united states':'usa','germany':'german','azerbaijan':'az','france':'french','spain':'spain','italy':'italian','russia':'russian'}
        google_key = "AIzaSyDyVE1WnWD1-3OgRws8DtRCF5zua_sO6fw"        


        #testing for input
        if (day<1 or day>31 or month<1 or month>12 or year<2021 or year >2022): # Check for intervals
            error_case_message='interval problem'
            error_case=True
        elif(((month==4 or month==6 or month==9 or month==11) and day==31) or (month==2 and ((year%4!=0 and day>=29) or  (year%4==0 and day>=30)))):
            error_case_message='month-day disagreement'
            error_case=True
        elif not country_code_lower in countries_dict.keys():
            error_case_message='wrong country'
            error_case=True


        #showing error:
        if error_case:
            return render(request, 'holidays.html', {'input_error': True, 'error_message': error_case_message}, status=400) 



        
        #determining the url

        
        r_url="https://www.googleapis.com/calendar/v3/calendars/en."+countries_dict[country_code_lower]+"%23holiday%40group.v.calendar.google.com/events?key="+google_key



        #getting information from url
        r=requests.get(r_url).json()
        country_holidays_general=r["items"]
        holiday_dict={}

        for x in country_holidays_general:
            holiday_dict.update({x["start"]["date"]:x["summary"]})
        

        #detemining correct date which is the first next day which is not holiday
        requested_day=date(year, month, day)
        requested_day_string=requested_day.strftime("%Y-%m-%d")
        is_holiday=(requested_day_string in holiday_dict)
        correct_date = requested_day
        correct_date_string = correct_date.strftime("%Y-%m-%d")
        while(correct_date_string in holiday_dict):
            correct_date += timedelta(days=1)
            correct_date_string = correct_date.strftime("%Y-%m-%d")

        #determining message 
        requested_day_string2=""
        holiday_name="-"
        next_day="-"
        is_holiday_string='-'

        requested_day_string2=requested_day.strftime("%d/%m/%Y %A")+""
        if(is_holiday):    
            holiday_name=holiday_dict.get(requested_day_string)
            next_day=correct_date.strftime("%d/%m/%Y %A")+""
            is_holiday_string='True'
        else:
            is_holiday_string='False'    

        legal_date_string=True

        return render(request, 'holidays.html', {'input_error': False,'legal_date':legal_date_string, 'requested_date':requested_day_string2, 'is_holiday':is_holiday_string, 'holiday_name': holiday_name, "next_day": next_day })
