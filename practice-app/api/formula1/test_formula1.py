from rest_framework import status
from rest_framework.test import APITestCase, APIClient

class Formula1Tests(APITestCase):
    def test_get_standings_page(self):
        """
        Ensure we can use GET request to endpoint http://localhost:8000/api/formula1/ properly
        """
        client = APIClient()
        response = client.get('/formula1/')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
    
    def test_get_driver_info_page(self):
        """
        Ensure we cannot use GET request to endpoint http://localhost:8000/api/formula1/driver_info/
        """
        client = APIClient()
        response = client.get('/formula1/driver_info/')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_valid_input(self):
        """
        Ensure we can search a valid driver name/surname
        """
        data = {'driver_name':'Hamilton'}
        client = APIClient()
        response = client.post('/formula1/driver_info/', data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
    
    def test_valid_input_ignore_case(self):
        """
        Ensure we can search a valid driver name and surname, ignoring case
        """
        data = {'driver_name':'leWis HamiltoN'}
        client = APIClient()
        response = client.post('/formula1/driver_info/', data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_invalid_input(self):
        """
        Ensure we cannot search an invalid driver name
        """
        data = {'driver_name':'xxxxx'}
        client = APIClient()
        response = client.post('/formula1/driver_info/', data)
        self.assertEqual(response.status_code, status.HTTP_404_NOT_FOUND)
    
    def test_no_input(self):
        """
        Ensure we cannot search without an input
        """
        data = {'driver_name':''}
        client = APIClient()
        response = client.post('/formula1/driver_info/', data)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    
    
    
