package com.example.sportshub

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.*
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser.parseString

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

object SingletonRequestQueueProvider {
    private lateinit var requestQueue : RequestQueue
    private lateinit var access_token : String
    private lateinit var username : String
    private lateinit var password : String
    private val QUERY_FOR_LOGIN = "${R.string.localBackend}/api/auth/login/"
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

class CustomRequest( method : Int, url:String, requestBody : JSONObject
                        , listener: Response.Listener<JSONArray>,errorListener: Response.ErrorListener
                       ) : JsonRequest<JSONArray>(
    method,
    url,
    requestBody.toString(),
    listener,
    errorListener){

    override fun parseNetworkResponse(response: NetworkResponse): Response<JSONArray?>? {
        return try {
            val jsonString = String(
                response.data,
            )
            Response.success(
                JSONArray(jsonString), HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (je: JSONException) {
            Response.error(ParseError(je))
        }
    }




}