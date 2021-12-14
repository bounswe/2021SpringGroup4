from .models import Equipment,EquipmentBody
from rest_framework import serializers

class EquipmentBodySerializer(serializers.ModelSerializer):
    class Meta:
        model = EquipmentBody
        fields = ['title','description', 'location', 'sportType']


class EquipmentSerializer(serializers.ModelSerializer):
    body = EquipmentBodySerializer()

    class Meta:
        model = Equipment
        fields = [ 'context','id' ,'owner', 'type', 'body']  

    def validate(self, data):
        if self.context.get('request').method == "POST":
            response = {}
            required_fields = ['title', 'location', 'description', 'sportType']
            for field in required_fields:
                if field not in data:
                    response[field] = "This field is required."
            if response:
                raise serializers.ValidationError(response)
            return data
        else: return data     

    def create(self, validated_data):
        body = EquipmentBody.objects.create(**validated_data)
        request = self.context.get('request')
        owner = request.user
        equipment = Equipment.objects.create(owner=owner, body=body)
        return equipment 

    def update(self, instance, validated_data):
        fields = ['title', 'description', 'location', 'sportType']
        body = instance.body
        update = False
        for field in fields:
            if field in validated_data:
                setattr(body, field, validated_data[field])          
        return instance               


