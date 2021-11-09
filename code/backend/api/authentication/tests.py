from django.urls import reverse 
from rest_framework import status 
from rest_framework.test import APITestCase
from api.authentication.models import User 

class RegistrationTests(APITestCase):
    def test_register(self):
        """
            Ensure registration works properly.
        """
        url = reverse('register_user') 
        data = {'username': 'TestRegisterUser', 
                'email': 'testregister@gmail.com',
                'password': 'testpassword',
                'first_name': 'Test'}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(User.objects.get(username='TestRegisterUser'))
        created_user = User.objects.get(username='TestRegisterUser')
        self.assertEqual(created_user.email, 'testregister@gmail.com')
        self.assertEqual(created_user.first_name, 'Test')


