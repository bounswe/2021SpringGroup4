package com.example.sportshub.equipment

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.equipment.model.CreateEquipmentRequestModel
import com.example.sportshub.event.model.CreateEventRequestModel
import com.google.gson.GsonBuilder
import org.json.JSONObject

class CreateEquipmentViewModel : ViewModel() {
    var requestmodel : CreateEquipmentRequestModel = CreateEquipmentRequestModel()
    private val QUERY_CREATE_EQUIPMENT = "http://3.67.188.187:8000/api/equipment/"

    fun createEquipment(){
        val url = QUERY_CREATE_EQUIPMENT

        val gson = GsonBuilder().create()
        val createData = JSONObject(gson.toJson(requestmodel))


        val request = object: JsonObjectRequest(
            Method.POST, url, createData,
            {

            },
            {

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