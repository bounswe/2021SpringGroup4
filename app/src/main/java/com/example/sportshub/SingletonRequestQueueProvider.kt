package com.example.sportshub

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

object SingletonRequestQueueProvider {
    private lateinit var requestQueue : RequestQueue
    private lateinit var access_token : String
    private lateinit var username : String
    private lateinit var password : String
    private val QUERY_FOR_LOGIN = "http://3.67.188.187:8000/api/auth/login/"
    fun createQueue(context: Context){
        requestQueue = Volley.newRequestQueue(context)
    }
    fun getQueue(): RequestQueue{
        return requestQueue
    }
    fun setAccessToken(token:String){
        access_token = token
    }
    fun getAccessToken():String{
        return access_token
    }
    fun saveCredentials(uname:String,pword:String){
        username = uname
        password = pword
    }
    fun getUsername():String{
        return username
    }
    fun refreshAccessToken(){
        val loginData = JSONObject()
        try {
            loginData.put("username", username)
            loginData.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val request = JsonObjectRequest(
            Request.Method.POST, QUERY_FOR_LOGIN, loginData,
            {
                try {
                    access_token = it.getString("access")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, {

            }
        )
        requestQueue.add(request)
    }

}