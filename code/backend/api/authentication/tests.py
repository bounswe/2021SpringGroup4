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
                'password': 'testpassword'}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        created_user = User.objects.get(username='TestRegisterUser')
        self.assertEqual(created_user.email, 'testregister@gmail.com')

    
    def test_duplicate_fields(self):
        """
            Ensure registration blocks duplicate username or email.
        """
        user = User.objects.create(username='TestRegisterUser', email="nonduplicate@gmail.com", password="123456")
        url = reverse('register_user') 
        data = {'username': 'TestRegisterUser', 
                'email': 'testregister@gmail.com',
                'password': 'testpassword',}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

        user.delete()
        user = User.objects.create(username='NonDuplicate', email="testregister@gmail.com", password="123456")
        url = reverse('register_user') 
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)


class LoginTests(APITestCase):
    def test_login(self):
        """
            Ensure login works properly.
        """
        register_url = reverse('register_user')
        data = {'username': 'TestUser', 
                'email': 'testregister@gmail.com',
                'password': 'testpassword'}
        response = self.client.post(register_url, data, format='json')      

        url = reverse('login') 
        data = {'username': 'TestUser', 'password': 'testpassword'}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertTrue("access" in response.data)
        self.assertTrue("refresh" in response.data)
 
    def test_login_fail(self):
        """
            Ensure login is not allowed with invalid credentials.
        """
        url = reverse('login') 
        data = {'username': 'TestUser', 'password': 'testpassword'}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)



