from django.shortcuts import render
from rest_framework import views  
from rest_framework import generics
from .serializers import EventSerializer, CommentSerializer
from .models import Event, EventBody, Comment
from api.authentication.models import User 
from rest_framework.response import Response
from rest_framework import status
from .permissions import IsOwnerOrReadOnly
from rest_framework.exceptions import APIException
from rest_framework.permissions import AllowAny, IsAuthenticatedOrReadOnly
from django.db.models import Q
from activity_handler.handlers import event_activity_handler


class CommentNotFoundException(APIException):
    status_code = 404
    default_detail = "Comment with the given ID does not exist. Please provide a valid ID."
    default_code = "comment_not_found"

class CommentCreateView(views.APIView):
    permission_classes = [IsAuthenticatedOrReadOnly]

    def post(self, request, format=None):
        serializer = CommentSerializer(data=request.data, context={"request": request})
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class CommentDetailView(views.APIView):
    permissions_classes = [IsOwnerOrReadOnly]

    def get(self, request, pk, format=None):
        comment = Comment.objects.get(pk=pk)
        serializer = CommentSerializer(comment)
        return Response(serializer.data)

    def delete(self, request, pk):
        try: 
            comment = Comment.objects.get(pk=pk)
        except Comment.DoesNotExist:
            return Response({"status": "Comment with the given ID does not exists"}, status=status.HTTP_400_BAD_REQUEST)
        return Response({"status": "Event deleted successfully."}, status=status.HTTP_204_NO_CONTENT)

    def patch(self, request, pk):
        try: 
            event = Comment.objects.get(pk=pk)
        except Comment.DoesNotExist:
            return Response({"status": "Comment with the given ID does not exists"}, status=status.HTTP_400_BAD_REQUEST)

        serializer = CommentSerializer(Comment, request.data, context={"request": request})
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_202_ACCEPTED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)