package com.example.sportshub.ui.login

import com.example.sportshub.backend_connection.login_register.RegisterModel

abstract class RegisterListener {

    abstract fun onError()

    abstract fun onResponse(registerModel: RegisterModel?)

}