from django.urls import reverse 
from rest_framework import status 
from rest_framework.test import APITestCase, force_authenticate
from api.authentication.models import User 
from .models import Event
import datetime

class EventTests(APITestCase):
    def test_event_list(self):
        #TODO: check successful get request to events endpoint
        pass

    def test_event_create(self):
        user = User.objects.create(username="user", password="pass",
                                    email="test@email.com", first_name="Test")
        self.client.force_authenticate(user=user)
        data = {
            "title": "TestEvent",
            "location": "TestLocation",
            "maxPlayers": "10",
            "date": "2021-10-05",
            "time": "21:00",
            "duration": "02:00",            
        }
        url = reverse('event_list_create')
        response = self.client.post(url, data=data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        event = Event.objects.get(id=response.data.get('id'))
        self.assertEqual(event.body.title, "TestEvent")
        self.assertEqual(event.body.location, "TestLocation")   
        self.assertEqual(event.body.maxPlayers, 10)
        self.assertEqual(event.body.date, datetime.date(2021, 10, 5))
        self.assertEqual(event.body.time, datetime.time(21, 0))
        self.assertEqual(event.body.duration, datetime.time(2,0))
    
    def test_add_remove(self):
        pass #TODO 

    def test_comment(self): 
        pass #TODO 

    def test_authorization(self):
        pass #TODO 
 
        
        