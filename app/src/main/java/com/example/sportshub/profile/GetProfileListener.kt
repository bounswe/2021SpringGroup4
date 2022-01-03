package com.example.sportshub.profile

import com.example.sportshub.profile.model.ProfileModel

abstract class GetProfileListener {

    abstract fun onError(statusCode: Int?)

    abstract fun onResponse(profileModel: ProfileModel)

}