package com.example.sportshub.ui.login

import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    fun tryregister(email: String, password: String, confirm: String): Boolean {
        return password.equals(confirm)
    }
    // TODO: Implement the ViewModel
}