from django.urls import path, include

from api.authentication.models import User
from .views import UserDetailView

urlpatterns = [
    path('<username>/', UserDetailView.as_view(), name='user_detail'),
]

