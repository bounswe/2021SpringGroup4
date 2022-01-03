package com.example.sportshub.profile.model

import com.example.sportshub.event.model.CommentModel
import com.example.sportshub.event.model.EventModel
import kotlinx.parcelize.RawValue

data class ProfileModel(
    var id: Int = 0,
    var username: String = "",
    var email: String = "",
    var first_name: String = "",
    var last_name: String = "",
    var age: Int = 0,
    var about: String = "",
    var location: String = "",
    var profile_picture: String = "",
    var going: MutableList<Int> = mutableListOf(),
    var applied: MutableList<Int> = mutableListOf(),
    var badges_list: MutableList<BadgeModel> = mutableListOf()
)
