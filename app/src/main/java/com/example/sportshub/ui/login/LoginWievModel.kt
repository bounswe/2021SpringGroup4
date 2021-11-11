package com.example.sportshub.ui.login
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel(){
    fun tryLogin(email: String, password: String): Boolean {
        //TODO validate user and if valid then save credential for subsequent logins
        return true;
    }

    // TODO: Implement the ViewModel
}