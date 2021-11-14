package com.example.sportshub.backend_connection.event

data class EventModel(
    var name: String,
    var location: String,
    var date: String,
    var time: String,
    var duration: Int, // unit-> minutes
    var sport_type: SportTypeModel,
    var number_of_people: Int,
    var skill_level: Float  // score out of 5
)
