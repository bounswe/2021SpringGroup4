from django.urls.base import reverse
from rest_framework import status
from rest_framework.test import APITestCase

from api.authentication.models import User
from .models import Notification, NotificationTEMPLATES


class NotificationTests(APITestCase):
    def test_notification_list(self):
        """
            Ensure users can list their notifications.
        """
        url = reverse('register_user')
        data = {'username': 'test_notifications_user', 
                'email': 'testregister@gmail.com',
                'password': 'testpassword'}
        response = self.client.post(url, data, format='json')
        user = User.objects.get(username='test_notifications_user')
        self.client.force_authenticate(user=user)

        created_notifications = set()
        for i in range(10):
            notification = Notification.objects.create(recipient = user, body = NotificationTEMPLATES.test + str(i+1))
            created_notifications.add(notification.id-1)
        # LIST
        url = reverse('notification-list')
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

        current_user_notifications = set()
        for a in response.data:
            for x in a:
                if x != 'id':
                    continue
                current_user_notifications.add(a[x])
        
        for id in created_notifications:
            self.assertEqual(id in current_user_notifications, True)


    def test_notification_RD(self):
        """
            Ensure users can retrieve and delete their notifications
        """
        url = reverse('register_user')
        data = {'username': 'test_notification_RD', 
                'email': 'testregister@gmail.com',
                'password': 'testpassword'}
        self.client.post(url, data, format='json')
        user = User.objects.get(username='test_notification_RD')
        self.client.force_authenticate(user=user)

        notification = Notification.objects.create(recipient = user, body = NotificationTEMPLATES.test)
        # RETRIEVE
        url = reverse('notification-detail', kwargs={'pk': notification.id})
        response = self.client.get(url)

        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data['id'], notification.id)
        self.assertEqual(response.data['body'], NotificationTEMPLATES.test)
        # DELETE
        response = self.client.delete(url)

        self.assertEqual(response.status_code, status.HTTP_204_NO_CONTENT)
