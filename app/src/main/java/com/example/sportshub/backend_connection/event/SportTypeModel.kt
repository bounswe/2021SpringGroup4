package com.example.sportshub.backend_connection.event

data class SportTypeModel(
    var name: String = "",
    var required_equipments: MutableList<String> = mutableListOf(),
    var rules: String = ""
)