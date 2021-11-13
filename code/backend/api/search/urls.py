from django.urls import path, include
from .views import UserSearch, EventSearch

urlpatterns = [
    path('user/<field>/<value>', UserSearch.as_view(), name="user_search"),
    path('event/<field>/', EventSearch.as_view(), name="event_search"),
]

