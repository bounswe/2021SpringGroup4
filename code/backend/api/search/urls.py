from django.urls import path, include
from .views import UserSearch, EventSearch, EquipmentSearch

urlpatterns = [
    path('user/<field>/<value>', UserSearch.as_view(), name="user_search"),
    path('event/<field>/', EventSearch.as_view(), name="event_search"),
    path('equipment/sport/', EquipmentSearch.as_view(), name="equipment_search"),
]

