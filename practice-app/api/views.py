from django.http.response import JsonResponse
from django.shortcuts import render

from rest_framework.decorators import api_view
from rest_framework.response import Response


@api_view(['GET'])
def apiOverview(request):
    urls = {
        'Login' : '/login/',
        'Register' : '/register/'
    }
    return Response(urls)
