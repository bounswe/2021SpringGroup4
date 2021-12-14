from django.urls import path, include
from .views import EquipmentCreateView, EquipmentDetailView

urlpatterns = [
    path('', EquipmentCreateView.as_view(), name='equipment_list'),
    path('<int:pk>/', EquipmentDetailView.as_view(), name='equipment_rud'),

]