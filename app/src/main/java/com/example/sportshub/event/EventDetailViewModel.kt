package com.example.sportshub.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportshub.event.model.EventModel

class EventDetailViewModel : ViewModel() {
    val event : MutableLiveData<EventModel>? = MutableLiveData(EventModel())


}