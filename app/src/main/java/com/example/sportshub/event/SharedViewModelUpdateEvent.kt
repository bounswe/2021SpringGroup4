package com.example.sportshub.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportshub.event.model.CreateEventRequestModel
import com.example.sportshub.event.model.EventModel

class SharedViewModelUpdateEvent : ViewModel() {

    val event = MutableLiveData<EventModel>()
    var lat: Double = 0.0
    var long: Double = 0.0

}