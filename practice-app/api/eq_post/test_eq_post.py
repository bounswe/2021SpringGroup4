from api.models import Post
from api.serializers import PostSerializer
from rest_framework.test import APITestCase
from django.urls import reverse
from rest_framework import status

class PostTests(APITestCase):
    def test_valid_post(self):
        """
        Ensure post is valid.
        """
        data = { 'username': 'test_user', 'title': 'test_title', 'description': 'I am a test description', 'location': 'test_location'}
        response = self.client.post('/api/eq_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_valid_post_with_extra_fields(self):
        """
        Ensure post is valid with additional fields appended to the request data.
        """
        data = { 'username': 'test_user', 'title': 'test_title', 'description': 'I am a test description', 'location': 'test_location', 'useless': 'useless field'}
        response = self.client.post('/api/eq_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_null_username(self):
        """
        Ensure we don't allow posts without a username.
        """
        data = { 'username': '', 'title': 'test_title', 'description': 'I am a test description', 'location': 'test_location'}
        response = self.client.post('/api/eq_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_null_title(self):
        """
        Ensure we don't allow registrations without a title.
        """
        data = { 'username': 'test_user', 'title': '', 'description': 'I am a test description', 'location': 'test_location',}
        response = self.client.post('/api/eq_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST) 

    def test_incomplete_registration(self):
        """
        Ensure that users can post equipment without entering location or a description.
        """
        data = { 'username': 'test_user', 'title': 'test_title', 'description': '', 'location': ''}
        response = self.client.post('/api/eq_post/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)