from models import EventPost
from serializers import EventPostSerializer
from rest_framework.test import APITestCase
from django.urls import reverse
from rest_framework import status

class EventPostTests(APITestCase):
    def test_valid_post(self):
        """
        Ensure post is valid.
        """
        data = { 'EventName': 'test_EventName', 'NumOfPlayers': 3 ,'Description': 'test_Description' , 'Date' : 'test_Date' , 'Time': 'test_Time'}
        response = self.client.post('/api/event_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)


