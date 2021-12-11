package com.example.sportshub.backend_connection.event

data class SearchEventModel(
    var location: String = "",
    var dist: Int = 0, // unit -> km
    var name: String = "",
    var date: String = "",
    var sportType: String = "",
    var skillLevel: Float = 0F // score out of 5
)

