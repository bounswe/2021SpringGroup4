package com.example.sportshub.ui.event

import com.example.sportshub.backend_connection.event.EventModel

abstract class EventListListener {

    abstract fun onError()

    abstract fun onResponse(eventList:MutableList<EventModel>)

}