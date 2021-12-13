from django.urls import path, include

from .views import BadgeCreateView

urlpatterns = [
    path('', BadgeCreateView.as_view(), name='badge_create')
]