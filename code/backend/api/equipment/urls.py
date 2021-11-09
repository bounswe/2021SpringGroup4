from django.urls import path, include
from .views import EquipmentPostListCreateView, EquipmentPostRetrieveUpdateDestroyView

urlpatterns = [
    path('', EquipmentPostListCreateView.as_view(), name='equipment_list'),
    path('<int:pk>/', EquipmentPostRetrieveUpdateDestroyView.as_view(), name='equipment_rud'),
]