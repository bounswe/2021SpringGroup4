from django.urls import path
from .views import LocationUpdateRetreiveView, ListCreateGenericViews, LocationFiltering

urlpatterns = [
    path("", ListCreateGenericViews.as_view()),
    path(
        "<int:pk>",
        LocationFiltering.as_view(),
    ),
]