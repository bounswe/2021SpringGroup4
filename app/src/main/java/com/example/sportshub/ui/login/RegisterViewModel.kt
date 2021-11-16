package com.example.sportshub.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.sportshub.backend_connection.login_register.LoginRegisterAPIConnection
import com.example.sportshub.backend_connection.login_register.RegisterModel
import kotlinx.coroutines.awaitAll

class RegisterViewModel : ViewModel() {
    var regModel : RegisterModel = RegisterModel()
    var errorMessage : String = ""
    var success : Boolean = false
    inner class RegisterListenerClass : LoginRegisterAPIConnection.RegisterListener{
        override fun onResponse(registerModel: RegisterModel?) {
            regModel = registerModel!!
            success = true
        }
        override fun onError(message: String?) {
            errorMessage = message!!
        }

    }

    fun tryregister(username:String, email: String, password: String, confirm: String,context: Context): Boolean {
        if (!password.equals(confirm)){
            return false
        }
        return true
    }
    // TODO: Implement the ViewModel
}

