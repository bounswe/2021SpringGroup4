

from rest_framework.test import APITestCase
from rest_framework import status

class HolidaysTests(APITestCase):
    #testing whether the host answers requests with correct input

    def test_valid_input_correct(self):
        data = { 'day': "9", 'month': "4", 'year': "2021",'country_code':'united states'}
        response = self.client.post('/api/holidays/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    # testing the wrong date format extending date limit
    def test_valid_interval_problem(self):
        data = { 'day': '39', 'month': '4', 'year': '2021','country_code':'united states'}
        response = self.client.post('/api/holidays/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    # testing the wrong date such as 30 febr
    def test_valid_month_day_disagreement(self):
        data = { 'day': '29', 'month': '2', 'year': '2021','country_code':'united states'}
        response = self.client.post('/api/holidays/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)        

    # testing the country is not in the choices
    def test_valid_wrong_country(self):
        data = { 'day': '9', 'month': '4', 'year': '2021','country_code':'south korean'}
        response = self.client.post('/api/holidays/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    # testing empty input    
    def test_valid_null_element(self):
        data = { 'day': '', 'month': '4', 'year': '2021','country_code':'turkey'}
        response = self.client.post('/api/holidays/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    # testing wrong type input    
    def test_valid_null_element(self):
        data = { 'day': '2', 'month': 'april', 'year': '2021','country_code':'turkey'}
        response = self.client.post('/api/holidays/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)        