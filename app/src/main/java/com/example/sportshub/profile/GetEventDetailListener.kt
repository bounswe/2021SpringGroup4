package com.example.sportshub.profile

import com.example.sportshub.event.model.EventModel
import com.example.sportshub.profile.model.ProfileModel

abstract class GetEventDetailListener {

    abstract fun onError()

    abstract fun onResponse(eventModel: EventModel)

}