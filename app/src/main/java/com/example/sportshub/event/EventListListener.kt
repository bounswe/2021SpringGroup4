package com.example.sportshub.event

import com.example.sportshub.event.model.EventModel

abstract class EventListListener {

    abstract fun onError()

    abstract fun onResponse(eventList:MutableList<EventModel>)

}