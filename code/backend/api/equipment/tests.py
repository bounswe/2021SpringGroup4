from django.test import TestCase

from django.urls import reverse 
from rest_framework import status
from rest_framework.generics import get_object_or_404 
from rest_framework.test import APITestCase
from api.authentication.models import User 
from .models import EquipmentPost

class EquipmentTests(APITestCase):
    #creating without user
    def test_equipment_create(self):
        user = User.objects.create(username="eqp_test_create", password="12345",
                                   email="eqp_test@email.com")
        self.client.force_authenticate(user=user)
        data = {
            "title": "test_equipment_create",
            "location": "Test Equipment Location",
            "description": "This equipment is created for testing",     
        }
        url = reverse('equipment_list')
        response = self.client.post(url, data=data, format='json')

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        event = EquipmentPost.objects.get(id=response.data.get('id'))

        self.assertEqual(event.title, "test_equipment_create")
        self.assertEqual(event.location, "Test Equipment Location")   
        self.assertEqual(event.description, "This equipment is created for testing")

    def test_equipment_remove(self):
        user = User.objects.create(username="eqp_test_add_remove", password="12345",
                                    email="eqp_test@email.com")
        self.client.force_authenticate(user=user)

        data = {
            "title": "test_equipment_remove",
            "location": "Test Equipment Location",
            "description": "This equipment is created for testing",     
        }

        url = reverse('equipment_list')
        response = self.client.post(url, data=data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        eqp_id = response.data.get('id')
        url = reverse('equipment_rud', kwargs={'pk': eqp_id})

        response = self.client.delete(url, format='json')
        self.assertEqual(response.status_code, status.HTTP_204_NO_CONTENT)        