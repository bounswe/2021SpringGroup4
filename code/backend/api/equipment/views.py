from django.shortcuts import render
from api.authentication.models import User
from rest_framework import views  
from .models import Equipment
from rest_framework import generics 
from .serializers import EquipmentSerializer
from rest_framework.permissions import IsAuthenticatedOrReadOnly
from rest_framework.response import Response
from rest_framework import status

class EquipmentCreateView(views.APIView):
    permission_classes = [IsAuthenticatedOrReadOnly]

    def get(self, request, format=None):
        equipments = Equipment.objects.all()
        serializer = EquipmentSerializer(equipments, many=True)
        return Response(serializer.data)

    def post(self, request, format=None):
        serializer = EquipmentSerializer(data=request.data, context={"request": request})
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)    
    

class EquipmentDetailView(views.APIView):
    permissions_classes = [IsAuthenticatedOrReadOnly]

    def get(self, request, pk, format=None):
        event = Equipment.objects.get(pk=pk)
        serializer = EquipmentSerializer(event)
        return Response(serializer.data)

    def delete(self, request, pk):
        try: 
            equipment = Equipment.objects.get(pk=pk)
        except Equipment.DoesNotExist:
            return Response({"status": "Equipment with the given ID does not exists"}, status=status.HTTP_400_BAD_REQUEST)
        equipment.delete()
        return Response({"status": "Equipment deleted successfully."}, status=status.HTTP_204_NO_CONTENT)

    def patch(self, request, pk):
        try: 
            event = Equipment.objects.get(pk=pk)
        except Equipment.DoesNotExist:
            return Response({"status": "Equipment with the given ID does not exists"}, status=status.HTTP_400_BAD_REQUEST)

        serializer = EquipmentSerializer(event, request.data, context={"request": request})
        serializer.is_valid()
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_202_ACCEPTED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
