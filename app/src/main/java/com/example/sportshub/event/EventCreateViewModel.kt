package com.example.sportshub.event

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.event.model.CreateEventRequestModel
import com.google.gson.GsonBuilder
import org.json.JSONObject

class EventCreateViewModel : ViewModel() {
    val eventCreateRequestModel : MutableLiveData<CreateEventRequestModel> = MutableLiveData(CreateEventRequestModel())

    private val QUERY_FOR_CREATE_EVENT = "http://3.67.188.187:8000/api/events/"
    private var warning: String = ""

    fun stringConstruction(number: Int, word: String){
        if(number != 0){
            warning = warning + ", $word"
        } else{
            warning = warning + "$word"
        }
    }

    fun createEvent(context: Context, createEventListener: CreateEventListener) {
        val url = QUERY_FOR_CREATE_EVENT
        warning = "Event "

        val gson = GsonBuilder().create()
        val createData = JSONObject(gson.toJson(eventCreateRequestModel.value))
        if(eventCreateRequestModel.value!!.title == ""){
            createData.remove("title")
        }
        if(eventCreateRequestModel.value!!.maxPlayers == 0){
            createData.remove("maxPlayers")
        }
        if(eventCreateRequestModel.value!!.location == ""){
            createData.remove("location")
        }
        if(eventCreateRequestModel.value!!.date == ""){
            createData.remove("date")
        }
        if(eventCreateRequestModel.value!!.time == ""){
            createData.remove("time")
        }
        if(eventCreateRequestModel.value!!.duration == "" || eventCreateRequestModel.value!!.duration == "0:0"){
            createData.remove("duration")
        }

        val request = object: JsonObjectRequest(
            Request.Method.POST, url, createData,
            {
                createEventListener.onResponse()
            },
            {
                val statusCode: Int? = it.networkResponse?.statusCode
                var num: Int = 0
                if(statusCode == 400){
                    val errorBody = JSONObject(String(it.networkResponse.data))
                    if(errorBody.has("title")){
                        warning = warning + "title"
                        num = num + 1
                    }
                    if(errorBody.has("maxPlayers")){
                        stringConstruction(num, "player number")
                        num = num + 1
                    }
                    if(errorBody.has("location")){
                        stringConstruction(num, "location")
                        num = num + 1
                    }
                    if(errorBody.has("date")){
                        stringConstruction(num, "date")
                        num = num + 1
                    }
                    if(errorBody.has("time")){
                        stringConstruction(num, "time")
                        num = num + 1
                    }
                    if(errorBody.has("duration")){
                        stringConstruction(num, "duration")
                        num = num + 1
                    }
                    warning = warning + " required!"
                } else{
                    Toast.makeText(context, "Something wrong. Try again!", Toast.LENGTH_SHORT).show()
                }
                createEventListener.onError(warning)
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${SingletonRequestQueueProvider.getAccessToken()}"
                headers["Content-Type"] = "application/json"
                return headers
            }
        }
        SingletonRequestQueueProvider.getQueue().add(request)
    }

}