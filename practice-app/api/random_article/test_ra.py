from rest_framework.test import APITestCase
from rest_framework import status

class rand_article_tests(APITestCase):
	def test_article_empty(self):
		# Ensure fetched article not empty
		response = self.client.get('/random_article/', format='json')
		if len(response.content) == 0:
			self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
