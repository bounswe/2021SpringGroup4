package com.example.sportshub.profile.model

import com.example.sportshub.event.model.EventModel

data class BadgeModel(
    var id: Int = 0,
    var giver: String = "",
    var owner: String = "",
    var event_id: Int = 0,
    var type: String = ""
)
