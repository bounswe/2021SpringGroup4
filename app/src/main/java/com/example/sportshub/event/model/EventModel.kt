package com.example.sportshub.event.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class EventModel(
    var id: Int = 0,
    var creator: String = "",
    var title: String = "",
    var description: String = "",
    var date: String = "",
    var time: String = "",
    var duration: String = "",
    var location: String = "",
    var sportType: String = "",
    var maxPlayers: Int = 0,
    var applicants: MutableList<String> = mutableListOf(),
    var participants: MutableList<String> = mutableListOf(),
    var comments: @RawValue MutableList<CommentModel> = mutableListOf(),
    var skillLevel: String = "",
    var lat: Double = 0.0,
    var long: Double = 0.0
):Parcelable
