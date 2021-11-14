from django.contrib import admin

from .models import Comment, Event, EventBody

admin.register(EventBody)
admin.register(Event)
admin.register(Comment)
