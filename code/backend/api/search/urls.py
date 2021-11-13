from django.urls import path, include
from .views import UserSearch

urlpatterns = [
    path('user/<field>/<value>', UserSearch.as_view(), name="user_search"),
]

