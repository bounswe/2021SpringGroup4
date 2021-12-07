from rest_framework import generics
from .models import EventActivity
from .serializers import EventActivitySerializer
from rest_framework.permissions import AllowAny


class ListView(generics.ListAPIView):
    queryset = EventActivity.objects.all()
    serializer_class = EventActivitySerializer
    permission_classes = [AllowAny]
