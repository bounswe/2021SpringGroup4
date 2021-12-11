package com.example.sportshub.ui.login

import com.example.sportshub.backend_connection.login_register.LoginResponseModel

abstract class LoginListener {

    abstract fun onError(loginResponseModel: LoginResponseModel?)

    abstract fun onResponse(loginResponseModel: LoginResponseModel?)

}