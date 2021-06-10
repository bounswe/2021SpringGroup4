from rest_framework.test import APITestCase
from rest_framework import status


class Covid19Tests(APITestCase):
    def test_valid_searchform(self):
        """
        Ensure we could search a valid country
        """
        data = {'TR'}
        response = self.client.post('/covid19/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK, " not success status response code")
        print("Test completed")

    def test_unvalid_length_search(self):
        """
        Ensure we could not search a unvalid country
        """
        data = {'TRZ'}                   # valid country code length should be 2
        response = self.client.post('/covid19/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK , " not success status response code")
        print("Test completed")


    def test_unvalid_country_search(self):
        """
        Ensure we could not search a unvalid country
        """
        data = {'ZZ'}     # no any country code with ZZ
        response = self.client.post('/covid19/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK , " not success status response code")
        print("Test completed")

    def test_web_service(self):
        """
        Ensure we have web connection to take data from source
        """
        response=self.client.get('/covid19/')
        self.assertEqual(response.status_code, status.HTTP_200_OK, " not success status response code")
        print("Test completed")
