package com.example.sportshub.event.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateEventRequestModel(
    var title: String = "",
    var description: String = "",
    var date: String = "",
    var time: String = "",
    var duration: String = "",
    var location: String = "",
    var sportType: String = "",
    var maxPlayers: Int = 0,
    var skill_level: String = "Beginner",
    var lat: Double = 0.0,
    var long: Double = 0.0
): Parcelable
