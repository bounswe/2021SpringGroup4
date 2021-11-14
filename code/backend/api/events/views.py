from rest_framework import mixins, views, generics
from rest_framework.response import Response
from rest_framework import status

from .serializers import CommentSerializer, EventSerializer
from .models import Comment, Event
from .permissions import IsOwnerOrReadOnly

from rest_framework.permissions import IsAuthenticatedOrReadOnly


class EventListCreateView(views.APIView):
    permission_classes = [IsAuthenticatedOrReadOnly]

    def get(self, request, format=None):
        events = Event.objects.all()
        serializer = EventSerializer(events, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = EventSerializer(data=request.data, context={"request": request})
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class EventDetailView(views.APIView):

    permissions_classes = [IsOwnerOrReadOnly]

    def get(self, request, pk, format=None):
        event = Event.objects.get(pk=pk)
        serializer = EventSerializer(event)
        return Response(serializer.data)

    def delete(self, request, pk):
        try: 
            event = Event.objects.get(pk=pk)
        except Event.DoesNotExist:
            return Response({"status": "Event with the given ID does not exists"},
                            status=status.HTTP_400_BAD_REQUEST)
        event.delete()
        return Response({"status": "Event deleted successfully."},
                        status=status.HTTP_204_NO_CONTENT)

    def patch(self, request, pk):
        try: 
            event = Event.objects.get(pk=pk)
        except Event.DoesNotExist:
            return Response({"status": "Event with the given ID does not exists"},
                            status=status.HTTP_400_BAD_REQUEST)

        serializer = EventSerializer(event, request.data, context={"request": request})
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_202_ACCEPTED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class CommentCreateListAPIView(generics.GenericAPIView, 
                               mixins.CreateModelMixin,mixins.ListModelMixin):

    permission_classes = [IsAuthenticatedOrReadOnly]
    queryset = Comment.objects.all()
    serializer_class = CommentSerializer
    
    def post(self, request, *args, **kwargs):
        return self.create(request, *args, **kwargs)

    def get(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)

    def perform_create(self, serializer):
        event_pk = self.kwargs.get('pk')
        event = generics.get_object_or_404(Event, pk=event_pk)
        user = self.request.user 

        serializer.save(parent=event.body, owner=user)


class CommentRetrieveUpdateDestroyAPIView(generics.RetrieveUpdateDestroyAPIView):
    permission_classes = [IsAuthenticatedOrReadOnly]
    queryset = Comment.objects.all()
    serializer_class = CommentSerializer
    

    