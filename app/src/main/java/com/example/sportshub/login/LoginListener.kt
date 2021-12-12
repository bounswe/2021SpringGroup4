package com.example.sportshub.login

import com.example.sportshub.login.model.LoginResponseModel

abstract class LoginListener {

    abstract fun onError(loginResponseModel: LoginResponseModel?)

    abstract fun onResponse(loginResponseModel: LoginResponseModel?)

}