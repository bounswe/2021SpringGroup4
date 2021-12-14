package com.example.sportshub.event

abstract class CreateEventListener {

    abstract fun onError(warning: String)

    abstract fun onResponse()

}