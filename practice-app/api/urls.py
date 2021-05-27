from django.urls import path
from . import views

urlpatterns = [
    path('', view=views.apiOverview, name='api-overview'),
    path('register/', view=views.register, name='register'),
    path('random_article/', view=views.rand_article, name='random_article'),
    path('eq_post/', view=views.eq_post, name='eq_post')
]