from django.urls import path, include
from .views import Equipment,EquipmentDetail

urlpatterns = [
    path('', Equipment.as_view(), name='equipment_list'),
    path('<int:pk>/', EquipmentDetail.as_view(), name='equipment_rud'),
]