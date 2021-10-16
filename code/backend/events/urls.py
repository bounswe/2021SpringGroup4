from django.urls import path
from django.views.generic import TemplateView

app_name = "events_api"

urlpatterns = [
    path('', TemplateView.as_view(template_name="events/index.html")),
]