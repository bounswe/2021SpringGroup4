from django.urls import reverse 
from rest_framework import status 
from rest_framework.test import APITestCase
from api.authentication.models import User 
from .models import Event
import datetime

class EventTests(APITestCase):
    
    def test_event_create(self):
        user = User.objects.create(username="test_event_create", password="pass",
                                   email="test@email.com")
        self.client.force_authenticate(user=user)
        data = {
            "title": "test_event_create",
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

        self.assertEqual(event.body.title, "test_event_create")
        self.assertEqual(event.body.location, "TestLocation")   
        self.assertEqual(event.body.maxPlayers, 10)
        self.assertEqual(event.body.date, datetime.date(2021, 10, 5))
        self.assertEqual(event.body.time, datetime.time(21, 0))
        self.assertEqual(event.body.duration, datetime.time(2,0))
    
    def test_event_remove(self):
        user = User.objects.create(username="test_add_remove", password="pass",
                                    email="test@email.com")
        self.client.force_authenticate(user=user)

        data = {
            "title": "test_event_remove",
            "location": "TestLocation",
            "maxPlayers": "10",
            "date": "2021-10-05",
            "time": "21:00",
            "duration": "02:00",            
        }
        url = reverse('event_list_create')
        response = self.client.post(url, data=data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        event_id = response.data.get('id')
        url = reverse('event_detail', kwargs={'pk': event_id})

        response = self.client.delete(url, format='json')
        self.assertEqual(response.status_code, status.HTTP_204_NO_CONTENT)

    def test_populate(self):
        user = User.objects.create(username="test_populate", password="pass",
                                    email="test@email.com")
        self.client.force_authenticate(user=user)

        data = {
            "location": "TestLocation",
            "maxPlayers": "10",
            "date": "2021-10-05",
            "time": "21:00",
            "duration": "02:00",            
        }

        for i in range(0,10):
            title = "test_populate" + str(i)
            data["title"] = title
            url = reverse('event_list_create')
            response = self.client.post(url, data=data, format='json')
            self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_event_list(self):
        url = reverse('event_list_create')
        response = self.client.get(url, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        
        event_counter = 0
        for event in response.data:
            self.assertEqual(event, Event.objects.get(id=event.get('id')))
            event_counter += 1
        
        self.assertEqual(event_counter, Event.objects.all().count())

    def test_comment(self): 
        pass #TODO 

    def test_authorization(self):
        pass #TODO 

        