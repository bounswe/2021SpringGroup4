package com.example.sportshub.event.model

data class CommentModel(
    var id: Int = 0,
    var owner: String = "",
    var parent: Int = 0,
    var body: String = ""
)
