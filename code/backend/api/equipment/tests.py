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
            "title":"test_equipment_create",
            "description":"This equipment is created for testing",
            "location":"Test Equipment Location",
            "sportType":"Test sport Type",
            "contact":"contact",
            "image_url":"image_url"    
        }
        url = reverse('equipment_list')
        response = self.client.post(url, data=data, format='json')

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        equipment = EquipmentPost.objects.get(title='test_equipment_create')

        self.assertEqual(equipment.title, "test_equipment_create")
        self.assertEqual(equipment.location, "Test Equipment Location")   
        self.assertEqual(equipment.description, "This equipment is created for testing")


    def test_equipment_remove(self):
        user = User.objects.create(username="eqp_test_add_remove", password="12345",
                                    email="eqp_test@email.com")
        self.client.force_authenticate(user=user)

        data = {
            "title": "test_equipment_remove",
            "location": "Test Equipment Location",
            "description": "This equipment is created for testing",     
            "sportType":"Test sport Type",
            "contact":"contact",
            "image_url":"image_url"                
        }

        url = reverse('equipment_list')
        response = self.client.post(url, data=data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        equipment = EquipmentPost.objects.get(title='test_equipment_remove')

        eqp_id = equipment.id
        url = reverse('equipment_rud', kwargs={'pk': eqp_id})

        response = self.client.delete(url, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)     
 


    def test_equipment_many(self):
        user = User.objects.create(username="test_equipment_many", password="12345",
                                    email="eqp_test@email.com")
        self.client.force_authenticate(user=user)

        data = {
            "location": "Test Equipment Location",
            "description": "This equipment is created for testing",     
            "sportType":"Test sport Type",
            "contact":"contact",
            "image_url":"image_url",
            "title": "test_equipment_many"                         
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

        list = response.data.get("items")
        for eqp in list:
            title = "test_equipment_many" + str(i)
            self.assertEqual(title, EquipmentPost.objects.get(title=title).title)
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
            "sportType":"Test sport Type",
            "contact":"contact",
            "image_url":"image_url",          
        }
        url = reverse('equipment_list')
        response = self.client.post(url, data=data, format='json')

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        #updating eqp
        
        equipment = EquipmentPost.objects.get(title='test_equipment_update')
        eqp_id = equipment.id
        url2 = reverse('equipment_rud', kwargs={'pk': eqp_id})

        data2 = {
            "description": "This equipment is created for testing. It is updated",     
        }        

        response = self.client.patch(url2, data=data2 ,format='json')

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data.get("object").get("description"), "This equipment is created for testing. It is updated")