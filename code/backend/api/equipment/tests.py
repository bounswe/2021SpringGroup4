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


    def test_equipment_many(self):
        user = User.objects.create(username="test_equipment_many", password="12345",
                                    email="eqp_test@email.com")
        self.client.force_authenticate(user=user)

        data = {
            "location": "Test Equipment Location",
            "description": "This equipment is created for testing",     
        }

        for i in range(0,10):
            title = "test_equipment_many" + str(i)
            data["title"] = title
            url = reverse('equipment_list')
            response = self.client.post(url, data=data, format='json')
            self.assertEqual(response.status_code, status.HTTP_201_CREATED)


        url = reverse('equipment_list')
        response = self.client.get(url, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        

        eqp_counter = 0
        for eqp in response.data:
            self.assertEqual(eqp.get('id'), EquipmentPost.objects.get(id=eqp.get('id')).id)
            eqp_counter += 1
        
        self.assertEqual(eqp_counter, EquipmentPost.objects.all().count())        


    def test_equipment_update(self):
        user = User.objects.create(username="eqp_test_update", password="12345",
                                   email="eqp_test@email.com")
        self.client.force_authenticate(user=user)

        #creating eqp
        data = {
            "title": "test_equipment_update",
            "location": "Test Equipment Location",
            "description": "This equipment is created for testing",     
        }
        url = reverse('equipment_list')
        response = self.client.post(url, data=data, format='json')

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        #updating eqp
        eqp_id = response.data.get('id')
        url2 = reverse('equipment_rud', kwargs={'pk': eqp_id})

        data2 = {
            "description": "This equipment is created for testing. It is updated",     
        }        

        response = self.client.patch(url2, data=data2 ,format='json')

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data.get("description"), "This equipment is created for testing. It is updated")     