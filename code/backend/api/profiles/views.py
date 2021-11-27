from api.authentication.models import User 
from rest_framework import generics 
from .serializers import UserSerializer
from .permissions import IsSelfOrReadOnly

class UserDetailView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = [IsSelfOrReadOnly]
    queryset = User.objects.all()
    serializer_class = UserSerializer
    lookup_field = 'username'

class UserListView(generics.ListAPIView):
    queryset = User.objects.all()
    serializer_class = UserSerializer
