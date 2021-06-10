from api.models import User
from api.serializers import UserSerializer
from rest_framework.test import APITestCase
from django.urls import reverse
from rest_framework import status

class RegistrationTests(APITestCase):
    def test_valid_registration(self):
        """
        Ensure we can register a new user with valid information.
        """
        data = { 'username': 'test_user', 'password': 'test_password', 'email': 'test@email.com', 
        'description': 'I am a test description', 'location': 'test_location', 'age': '50' }
        response = self.client.post('/register/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_valid_registration_with_extra_fields(self):
        """
        Ensure we can register a new user with additional fields appended to the request data.
        """
        data = { 'username': 'test_user', 'password': 'test_password', 'email': 'test@email.com', 
        'description': 'I am a test description', 'location': 'test_location', 'age': '50', 'useless': 'useless field'}
        response = self.client.post('/register/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)    

    def test_username_conflict(self):
        """
        Ensure the system detects when a user is already registered with the username provided.
        """
        data = { 'username': 'test_user', 'password': 'test_password', 'email': 'test@email.com', 
        'description': 'I am a test description', 'location': 'test_location', 'age': '50', }
        self.client.post('/api/register/', data, format='json')
        data_conflict = { 'username': 'test_user', 'password': 'test_password', 'email': 'test_conflict@email.com', 
        'description': 'I am a test description', 'location': 'test_location', 'age': '50', }
        response = self.client.post('/register/', data_conflict, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_email_conflict(self):
        """
        Ensure the system detects when a user is already registered with the email provided.
        """
        data = { 'username': 'test_user', 'password': 'test_password', 'email': 'test@email.com', 
        'description': 'I am a test description', 'location': 'test_location', 'age': '50', }
        self.client.post('/api/register/', data, format='json')
        data_conflict = { 'username': 'test_user_conflict', 'password': 'test_password', 'email': 'test@email.com', 
        'description': 'I am a test description', 'location': 'test_location', 'age': '50', }
        response = self.client.post('/register/', data_conflict, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST) 

    def test_null_username(self):
        """
        Ensure we don't allow registrations without a username.
        """
        data = { 'username': '', 'password': 'test_password', 'email': 'test@email.com', 
        'description': 'I am a test description', 'location': 'test_location', 'age': '50', }
        response = self.client.post('/register/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)        

    def test_null_email(self):
        """
        Ensure we don't allow registrations without an email address.
        """
        data = { 'username': 'test_user', 'password': 'test_password', 'email': '', 
        'description': 'I am a test description', 'location': 'test_location', 'age': '50', }
        response = self.client.post('/register/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)   

    def test_null_password(self):
        """
        Ensure we don't allow registrations without a password.
        """
        data = { 'username': 'test_user', 'password': '', 'email': 'test@email.com', 
        'description': 'I am a test description', 'location': 'test_location', 'age': '50', }
        response = self.client.post('/register/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST) 

    def test_incomplete_registration(self):
        """
        Ensure that users can register without entering location, age or a description.
        """
        data = { 'username': 'test_user', 'password': 'test_pw', 'email': 'test@email.com', 
        'description': '', 'location': '', 'age': '' }
        response = self.client.post('/register/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
