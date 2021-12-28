package com.example.sportshub.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportshub.event.model.EventModel

class SharedViewModel : ViewModel() {

    val event = MutableLiveData<EventModel>()

    fun updateEvent(eventModel: EventModel) {
        event.value = eventModel
    }

}