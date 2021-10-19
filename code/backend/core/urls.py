from django.contrib import admin
from django.urls import path, include

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/events/', include("events_api.urls", namespace="events_api")),
    path('events/', include("events.urls", namespace="events"))
]
