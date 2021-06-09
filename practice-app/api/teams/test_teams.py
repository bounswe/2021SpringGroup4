from rest_framework import status
from rest_framework.test import APITestCase, APIClient

class NBATeamsTests(APITestCase):
    def test_valid_page(self):
        """
        ensure  that GET request is valid for endpoint http://localhost:8000/api/team/
        """
        client = APIClient()
        response = client.get('/api/team/')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        print("OK")


    def test_valid_team(self):
        """
        ensure that we get a response after selecting a team
        """
        data = { 'team_code': 'cle'}
        response = self.client.post('/api/team/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        print("OK")