package com.example.sportshub.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventViewModel : ViewModel() {
    fun getAllEvents(): MutableList<Int> {
        return MutableList(10,init = {index -> index})
    }

    fun searchByLocation(toString: String): MutableList<Int> {
        return MutableList(40,init = {index -> index})
    }
    //TODO Implement ViewModel
}