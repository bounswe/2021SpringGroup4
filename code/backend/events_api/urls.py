from django.urls import path
from .views import CreateEvent, RetrieveUpdateDestroyEvent

app_name = "events_api"

urlpatterns = [
    path('create/', CreateEvent.as_view(), name='create'),
    path('<int:pk>/', RetrieveUpdateDestroyEvent.as_view(), name='detail'),
]