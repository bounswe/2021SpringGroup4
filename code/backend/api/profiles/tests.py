from rest_framework.test import APITestCase
from rest_framework import generics, status 

from django.urls import reverse 

from api.authentication.models import User


class ProfileTests(APITestCase):

    def test_profile_RU(self):
        user = User.objects.create(username="test_profile_RUD", password="pass",
                                    email="test@email.com")
        self.client.force_authenticate(user=user)

        url = reverse('user_detail', kwargs={'username': user.username})

        # RETRIEVE
        response = self.client.get(url, format='json')

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data.get('id'), user.id)

        # UPDATE
        data = {
            "username": "test_profile_RUD_updated"
        }
        response = self.client.patch(url, data=data ,format='json')

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data.get("username"), data["username"])

        # TODO: Test update not only for username, but for all fields

    def test_profile_list(self):
        url = reverse('user_list')
        response = self.client.get(url, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)

        for profile in response.data:
            generics.get_object_or_404(User, id=profile.id)
