package com.example.sportshub.ui.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportshub.backend_connection.SingletonRequestQueue
import com.example.sportshub.backend_connection.login_register.RegisterModel
import org.json.JSONException
import org.json.JSONObject

class RegisterViewModel : ViewModel() {

    private val QUERY_FOR_REGISTER = "http://3.67.188.187:8000/api/auth/register/"

    fun tryRegister(context: Context, username:String, email: String, password: String, registerListener: RegisterListener) {

        val url = QUERY_FOR_REGISTER
        val registerModel = RegisterModel()
        val registerData = JSONObject()
        try {
            registerData.put("email", email)
            registerData.put("username", username)
            registerData.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val request = JsonObjectRequest(
            Request.Method.POST, url, registerData,
            {
                try {
                    registerModel.email = it.getString("email")
                    registerModel.username = it.getString("username")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                registerListener.onResponse(registerModel)
            },
            {
                val statusCode: Int? = it.networkResponse?.statusCode

                if(statusCode == 400){
                    if(it.networkResponse?.data != null){
                        val response = JSONObject(String(it.networkResponse.data))
                        if(response.has("email") && response.has("username")){
                            Toast.makeText(context, "There is already a user with given e-mail and username.", Toast.LENGTH_SHORT).show()
                        } else if(response.has("email")){
                            Toast.makeText(context, "There is already a user with given e-mail.", Toast.LENGTH_SHORT).show()
                        } else if(response.has("username")){
                            Toast.makeText(context, "There is already a user with given username.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else{
                    Toast.makeText(context, "Something wrong!", Toast.LENGTH_SHORT).show()
                }
                registerListener.onError()
            }
        )
        SingletonRequestQueue.getInstance(context).addToRequestQueue(request)
    }

}

