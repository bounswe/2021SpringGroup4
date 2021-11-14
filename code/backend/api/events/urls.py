from django.urls import path, include

from api.events.models import Event
from .views import EventListCreateView, EventDetailView, CommentCreateListAPIView, CommentRetrieveUpdateDestroyAPIView

urlpatterns = [
    path('', EventListCreateView.as_view(), name='event_list_create'),
    path('<int:pk>/', EventDetailView.as_view(), name='event_detail'),
    path('<int:pk>/comment/', CommentCreateListAPIView.as_view(), name='event_comment'),
    path('<int:pk>/comment/<int:comment_pk>', CommentRetrieveUpdateDestroyAPIView.as_view(), name='event_comment_RUD'),
]
