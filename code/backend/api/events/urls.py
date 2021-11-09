from django.urls import path, include

from api.events.models import Event
from .views import EventListCreateView, EventDetailView, NearbyEvents

urlpatterns = [
    path('', EventListCreateView.as_view(), name='event_list_create'),
    path('<int:pk>/', EventDetailView.as_view(), name='event_detail'),
    path('nearby/', NearbyEvents.as_view(), name='nearby_events'),
]

