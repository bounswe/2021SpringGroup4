from django.urls import path
from . import views

urlpatterns = [
    path('', view=views.apiOverview, name='api-overview'),
    path('register/', view=views.register, name='register'),
    path('places/', view=views.places, name='places'),
    path('random_article/', view=views.rand_article, name='random_article'),
    path('eq_post/', view=views.eq_post, name='eq_post'),
    path('covid19/', view=views.covid19, name='covid19'),
    path('holidays/', view=views.holidays, name="holidays"),
    path('event_post/', view=views.event_post, name='event_post'),
    path('covid19/<str:countrycode>', views.covid_country, name="covid_country"),
    path('search_user/', view=views.search_user, name='search_user'),
    path('formula1/', views.formula1, name="formula1"),
    path('formula1/driver_info/', views.driver_info, name="driver_info"),
    path('hava/', view=views.hava_api,name="hava"),
    path('team/', view=views.select_team, name='select_team'),
    path('team/<str:team_code>', view=views.list_team, name='list_team'),
]
