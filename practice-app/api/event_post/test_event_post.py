"@author: Yağmur Selek"
from models import EventPost
from serializers import EventPostSerializer
from rest_framework.test import APITestCase
from django.urls import reverse
from rest_framework import status
import datetime

class EventPostTests(APITestCase):
   
   
     #Test_1 Ensures post is valid.
       
    def test_1(self):
        data = { 'EventName': 'test_EventName1', 'SportType': 'SportType1','NumOfPlayers': 3 ,'Description': 'test_Description' , 'Date' : datetime.date(2022,2,22) , 'Time': datetime.time(2, 3)}
        response = self.client.post('/api/event_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)


     #Test_2 Ensures that you can not create an event for past dates 

    def test_2(self):
        data = { 'EventName': 'test_EventName2','SportType': 'SportType2', 'NumOfPlayers': 3 ,'Description': 'test_Description' , 'Date' : datetime.date(2020,9,8) , 'Time': datetime.time(2, 3)}
        response = self.client.post('/api/event_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_402_CREATED)


    #Test_3 Ensures that you can not create an event without providing an event name 

    def test_3(self):
        data = { 'EventName': None, 'SportType': 'SportType3','NumOfPlayers': 3 ,'Description': 'test_Description' , 'Date' : datetime.date(2022,9,8) , 'Time': datetime.time(2, 3)}
        response = self.client.post('/api/event_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_CREATED)

    
     
     #Test_4 Ensures that you can not create an event without providing an sport type

    def test_4(self):
        data = { 'EventName': 'test_EventName4','SportType': None, 'NumOfPlayers': 3 ,'Description': 'test_Description' , 'Date' : datetime.date(2022,9,8) , 'Time': datetime.time(2, 3)}
        response = self.client.post('/api/event_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_401_CREATED)

    
         
     #Test_5 Ensures that you can not create an event without providing an sport type

    def test_5(self):
        data = { 'EventName': 'test_EventName4','SportType': None, 'NumOfPlayers': 3 ,'Description': 'test_Description' , 'Date' : datetime.date(2022,9,8) , 'Time': datetime.time(2, 3)}
        response = self.client.post('/api/event_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_401_CREATED)