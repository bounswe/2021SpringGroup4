from django.urls import path, include

from api.events.models import Event
from .views import EventListCreateView, EventAddRemoveView, EventRetrieveUpdateDestroyView

urlpatterns = [
    path('', EventListCreateView.as_view(), name='event_list_create'),
    path('edit/<int:pk>/', EventAddRemoveView.as_view(), name='event_edit'),
    path('<int:pk>/', EventRetrieveUpdateDestroyView.as_view(), name='event_rud')
]

