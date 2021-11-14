from django.urls import path, include

from api.authentication.models import User
from .views import UserRetrieveUpdateView, UserListView

urlpatterns = [
    path('<username>/', UserRetrieveUpdateView.as_view(), name='user_detail'),
    path('', UserListView.as_view(), name='user_list'),
]
