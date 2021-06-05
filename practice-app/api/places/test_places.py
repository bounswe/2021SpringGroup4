from api.models import User
from api.serializers import UserSerializer
from rest_framework.test import APITestCase
from django.urls import reverse
from rest_framework import status

class SearchPlacesTests(APITestCase):
    def test_valid_search(self):
        """
        Ensure that we get a response after sending a non-blank request
        """
        data = { 'location': 'beşiktaş', 'keyword': 'üniversite'}
        response = self.client.post('/api/register/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_valid_search(self):
        """
        Ensure that the system doesn't accept an empty search.
        """
        data = { 'location': '', 'keyword': ''}
        response = self.client.post('/api/register/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)