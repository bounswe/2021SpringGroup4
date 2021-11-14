package com.example.sportshub.ui.login

import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    fun tryregister(username:String, email: String, password: String, confirm: String): Boolean {
        return password.equals(confirm)
    }
    // TODO: Implement the ViewModel
}