package com.example.sportshub.login

import com.example.sportshub.login.model.RegisterModel

abstract class RegisterListener {

    abstract fun onError()

    abstract fun onResponse(registerModel: RegisterModel?)

}