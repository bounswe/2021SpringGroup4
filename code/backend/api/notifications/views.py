from rest_framework import views  
from rest_framework import generics
from rest_framework.response import Response
from rest_framework.status import HTTP_403_FORBIDDEN

from api.authentication.models import User

from api.notifications.serializers import NotificationSerializer
from api.notifications.models import Notification

from rest_framework.permissions import IsAdminUser


class NotificationCreateAPIView(generics.CreateAPIView):

    permissions_classes = [IsAdminUser]
    queryset = Notification.objects.all()
    serializer_class = NotificationSerializer

    def perform_create(self, serializer):
        user_pk = self.kwargs.get('user_pk')
        user = generics.get_object_or_404(User, pk=user_pk)
        serializer.save(recipient=user)


# User can retrieve a list of their notifications.
# No user can see a notification that do not belong to them.
class NotificationListAPIView(views.APIView):

    def get(self, request, format=None):
        notifications = Notification.objects.filter(recipient=request.user)
        serializer = NotificationSerializer(notifications, many=True)
        return Response(serializer.data)


class NotificationDetailAPIView(generics.RetrieveDestroyAPIView):
    queryset = Notification.objects.all()
    serializer_class = NotificationSerializer

    def retrieve(self, request, *args, **kwargs):
        instance = self.get_object()
        if (request.user.is_superuser or request.user == instance.recipient):
            serializer = self.get_serializer(instance)
            return Response(serializer.data)
