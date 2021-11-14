package com.example.sportshub.backend_connection.event

data class EventModel(
    var id: Int = 0,
    var creator: String = "",
    var title: String = "",
    var description: String = "",
    var date: String = "",
    var time: String = "",
    var duration: String = "",
    var location: String = "",
    var sportType: SportTypeModel = SportTypeModel(),
    var maxPlayers: Int = 0,
    var applicants: MutableList<String> = mutableListOf(),
    var participants: MutableList<String> = mutableListOf(),
    var followers: MutableList<String> = mutableListOf(),
    var comments: MutableList<String> = mutableListOf(),
    var skillLevel: Float = 0F  // score out of 5
)
