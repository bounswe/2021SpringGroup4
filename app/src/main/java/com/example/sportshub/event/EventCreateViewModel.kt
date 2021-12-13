package com.example.sportshub.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportshub.event.model.CreateEventRequestModel

class EventCreateViewModel : ViewModel() {
    val eventCreateRequestModel : MutableLiveData<CreateEventRequestModel> = MutableLiveData(CreateEventRequestModel())

}