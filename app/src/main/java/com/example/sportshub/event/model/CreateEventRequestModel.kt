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
    var skillLevel: String = "",
    var lat: String = "",
    var long: String = ""
): Parcelable
