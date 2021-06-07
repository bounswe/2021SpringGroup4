from ...serializers import UserSerializer
from ...models import User
import unittest

from rest_framework.test import APITestCase
from rest_framework import status
import random, string

class Search_User_Test_Cases(APITestCase):

    if __name__ == '__main__':
        unittest.main()

    def test_typical_search(self):
        """
        Ensure we can search a user with valid input.
        First create a user with a random name, using register API
        """
        username = ''.join(random.choice(string.ascii_letters) for x in range(5))
        password = ''.join(random.choice(string.ascii_letters) for x in range(5))
        email = ''.join(random.choice(string.ascii_letters) for x in range(5)) + '@' + ''.join(random.choice(string.ascii_letters) for x in range(5)) + '.com'
        firstname = ''.join(random.choice(string.ascii_letters) for x in range(5)).capitalize()
        lastname = ''.join(random.choice(string.ascii_letters) for x in range(5)).capitalize()
        fullname = firstname + " " + lastname

        user_data = { 'username': username, 'password': password, 'email': email, 'fullname': fullname}
        """
        Create a post request to the register API
        Control if registration is successful. Terminate test otherwise.
        """
        response = self.client.post('/api/register/', user_data, format='json')
        if response.status_code != status.HTTP_201_CREATED:
            self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        """
        Perform search using search_user API
        """
        data = { 'input' : username } 
        response = self.client.post('/api/search_user/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)


    def test_invalid_search(self):
        """
        Ensure we cannot search a user with poor input.
        Create a user with a bad username, using register API
        """
        username = ''.join(random.choice(string.ascii_letters) for x in range(5)).join('#')
        password = ''.join(random.choice(string.ascii_letters) for x in range(5))
        email = ''.join(random.choice(string.ascii_letters) for x in range(5)) + '@' + ''.join(random.choice(string.ascii_letters) for x in range(5)) + '.com'
        firstname = ''.join(random.choice(string.ascii_letters) for x in range(5)).capitalize()
        lastname = ''.join(random.choice(string.ascii_letters) for x in range(5)).capitalize()
        fullname = firstname + " " + lastname
        user_data = { 'username': username, 'password': password, 'email': email, 'fullname': fullname}
        """
        Create a post request to the register API
        Control if registration is successful. Terminate test otherwise.

        For now, there's no barrier in front of creating a user with a poor username.
        TODO:   sovle-implement the problem above.

        If the creation of the user fails, this test must fail:
        """
        response = self.client.post('/api/register/', user_data, format='json')
        if response.status_code != status.HTTP_201_CREATED:
            self.assertEqual(response.status_code, status.HTTP_201_CREATED)

        """
        Perform search using search_user API
        The search should fail:
        """
        data = { 'input' : username } 
        response = self.client.post('/api/search_user/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

        
    def test_user_not_found(self):
        """
        Ensure we cannot find a user that does not exist.
        Create a user using register API, but search something else.

        If that user exists, pick another username.
        """
        result = ["dummy string"]
        while (len(result) > 0) :
            username = ''.join(random.choice(string.ascii_letters) for x in range(5))
            # TODO
            # Line below can well be changed with a well-designed search algorithm.
            # For now, as we filter w.r.t the username, the list named "result" will contain
            # 1 element only, which is supposedly the user we search for.
            result = User.objects.filter(username = username)
        """
        Now we have a username that does not exist.
        Perform search using search_user API
        The search should fail:
        """
        data = { 'input' : username } 
        response = self.client.post('/api/search_user/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

