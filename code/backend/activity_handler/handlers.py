from api.authentication.models import User 
from api.events.models import Event, EventBody 
from .models import EventActivity 

def event_activity_handler(type, actor, object):
    if type == 'Create':
        summary = f"{actor.username} has created a new event."
        EventActivity.objects.create(type=type, actor=actor, object=object, summary=summary)
    if type == 'Update':
        summary = f"{actor.username} has updated an event."
        EventActivity.objects.create(type=type, actor=actor, object=object, summary=summary)
    if type == 'Apply':
        summary = f"{actor.username} has applied to join an event."
        EventActivity.objects.create(type=type, actor=actor, object=object, summary=summary)
    if type == 'Delete':
        summary = f"{actor.username} has deleted to an event."
        EventActivity.objects.create(type=type, actor=actor, object=object, summary=summary)
    if type == 'Join':
        summary = f"{actor.username} has joined an event."
        EventActivity.objects.create(type=type, actor=actor, object=object, summary=summary)
    if type == 'Comment':
        summary = f"{actor.username} has commented on an event."
        EventActivity.objects.create(type=type, actor=actor, object=object, summary=summary)