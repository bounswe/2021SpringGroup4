package com.example.sportshub.backend_connection.login_register

data class LoginResponseModel(
    var refresh: String = "",
    var access: String = "",
    var detail: String = ""
)
