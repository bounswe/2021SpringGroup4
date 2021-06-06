from django.urls import path
from . import views

urlpatterns = [
    path('', view=views.apiOverview, name='api-overview'),
    path('register/', view=views.register, name='register'),
    path('places/', view=views.places, name='places'),
    path('random_article/', view=views.rand_article, name='random_article'),
    path('eq_post/', view=views.eq_post, name='eq_post'),
    path('covid19/', view=views.covid19, name='covid19'),
    path('covid19/<str:countrycode>', views.covid_country, name="covid_country"),
    path('search_user/', view=views.search_user, name='search_user'),
]
