from django.urls import path

from .views import NotificationCreateAPIView, NotificationListAPIView, NotificationDetailAPIView

urlpatterns = [
    path('', NotificationListAPIView.as_view(), name='notification-list'),
    path('send_notification/<int:user_pk>/', NotificationCreateAPIView.as_view(), name='notification-send'),
    path('<int:pk>/', NotificationDetailAPIView.as_view(), name='notification-detail'),
]
