from django.contrib import admin
from django.urls import path, include
from rest_framework.schemas import get_schema_view
from rest_framework.documentation import include_docs_urls

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/auth/', include('api.authentication.urls')),
    path('api/profiles/', include('api.profiles.urls')),
    path('api/events/', include('api.events.urls')),
    path('api/schema', get_schema_view(
        title="Amateur Sports API",
        description="API for the project",
        version="1.0.0",
    ), name="openapi-schema"),
    path('api/docs/', include_docs_urls(title='API')),
    path('api/equipment/', include('api.equipment.urls')),
    path('api/search/', include('api.search.urls')),
    path('api/notifications/', include('api.notifications.urls')),
]
