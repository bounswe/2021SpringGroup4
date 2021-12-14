from django.urls import path, include

from api.events.models import Event
from .views import EventListCreateView, EventDetailView
from .comment_views import CommentCreateView, CommentDetailView

urlpatterns = [
    path('', EventListCreateView.as_view(), name='event_list_create'),
    path('<int:pk>/', EventDetailView.as_view(), name='event_detail'),
    path('comment/', CommentCreateView.as_view(), name='comment_create'),
    path('comment/<int:pk>/', CommentDetailView.as_view(), name='comment_detail')

]

