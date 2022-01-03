from django.shortcuts import render
from rest_framework.test import APITransactionTestCase
from rest_framework.views import APIView
from api.authentication.models import User 
from .models import EquipmentPost
from rest_framework import generics 
from .serializers import EquipmentSerializer
from api.events.permissions import IsOwnerOrReadOnly
from rest_framework.response import Response
from rest_framework import status
from django.utils import timezone
from django.http import JsonResponse



class EquipmentDetail(APIView):

    def get(self, request,pk):
        try:
            equipments = EquipmentPost.objects.filter(id=pk)
            eq = equipments[0]
        except:
            return Response(status=status.HTTP_204_NO_CONTENT)

        response= {"@context": "https://www.w3.org/ns/activitystreams", "summary": eq.owner.username+" posted an equipment",
            "type": "Create"}    

        actor = {"type": "Person","name":eq.owner.username}

        object = {"type":"Equipment", "title":eq.title, "description":eq.description, "location":eq.location,
            "contact":eq.contact,"image_url":eq.image_url, "sportType":eq.sportType, "creationDate":eq.creationDate}

        response["actor"]=actor
        response["object"]=object
        
        return Response(response)



    def patch(self, request,pk):
        try:
            equipments = EquipmentPost.objects.filter(id=pk)
            eq = equipments[0]
        except:
            return Response(status=status.HTTP_204_NO_CONTENT)



        equipmentSerializer = EquipmentSerializer(eq, request.data, partial=True)

        if equipmentSerializer.is_valid():
            equipmentSerializer.save()
            response= {"@context": "https://www.w3.org/ns/activitystreams", "summary": eq.owner.username+" posted an equipment",
                "type": "Create"}    

            actor = {"type": "Person","name":eq.owner.username}

            object = {"type":"Equipment", "title":eq.title, "description":eq.description, "location":eq.location,
                "contact":eq.contact,"image_url":eq.image_url, "sportType":eq.sportType, "creationDate":eq.creationDate}

            response["actor"]=actor
            response["object"]=object
        
            return Response(response)

        return Response(status=status.HTTP_400_BAD_REQUEST)



    def delete(self, request, pk):
        try:
            equipments = EquipmentPost.objects.filter(id=pk)
            eq = equipments[0]
            eq.delete()
        except:
            return Response(status=status.HTTP_204_NO_CONTENT)    

        empty = {}
        return Response(empty)


class Equipment(APIView):

    def get(self,request):
        try:
            equipments = EquipmentPost.objects.filter()
        except:
            return Response(status=status.HTTP_400_BAD_REQUEST)

        equipmentserializer = EquipmentSerializer(equipments, many=True)
        
        all_eq = []

        for eq in equipments:
            response= {"@context": "https://www.w3.org/ns/activitystreams", "summary": eq.owner.username+" posted an equipment",
            "type": "Create"}    

            actor = {"type": "Person","name":eq.owner.username}

            object = {"type":"Equipment", "title":eq.title, "description":eq.description, "location":eq.location,
            "contact":eq.contact,"image_url":eq.image_url, "sportType":eq.sportType, "creationDate":eq.creationDate}

            response["actor"]=actor
            response["object"]=object

            all_eq.append(response)
        
        respone_all = {"items":all_eq}
        return Response(respone_all)



    def post(self, request):
        body = {}
        body = request.data
        body["owner"] = self.request.user.id

        try:
            equipmentSerializer=EquipmentSerializer(data=body)
        except:
            return Response(status=status.HTTP_204_NO_CONTENT)

        if equipmentSerializer.is_valid():

            equipmentSerializer.save()
            response= {"@context": "https://www.w3.org/ns/activitystreams", "summary": equipmentSerializer.validated_data["owner"].username+" posted an equipment",
            "type": "Create"}   
            
            actor = {"type": "Person","name":equipmentSerializer.validated_data["owner"].username} 

            object = {"type":"Equipment", "title":equipmentSerializer.validated_data["title"], "description":equipmentSerializer.validated_data["description"],
             "location":equipmentSerializer.validated_data["location"], "contact":equipmentSerializer.validated_data["contact"],
             "image_url":equipmentSerializer.validated_data["image_url"], "sportType":equipmentSerializer.validated_data["sportType"],
              "creationDate":timezone.now()}

            response["object"] = object
            response["actor"] = actor

            
            return Response(response, status=status.HTTP_201_CREATED)
        
        
        return Response(status=status.HTTP_400_BAD_REQUEST)




