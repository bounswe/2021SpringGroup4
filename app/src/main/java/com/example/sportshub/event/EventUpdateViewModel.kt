package com.example.sportshub.event

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportshub.R
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.event.model.CreateEventRequestModel
import com.google.gson.GsonBuilder
import org.json.JSONObject

class EventUpdateViewModel : ViewModel() {
    val eventUpdateRequestModel : MutableLiveData<CreateEventRequestModel> = MutableLiveData(CreateEventRequestModel())

    private val QUERY_FOR_UPDATE_EVENT = "${R.string.localBackend}/api/events/"

    fun updateEvent(context: Context, eventId: Int, updateEventListener: UpdateEventListener) {
        val url = QUERY_FOR_UPDATE_EVENT + "$eventId/"

        val gson = GsonBuilder().create()
        val updateData = JSONObject(gson.toJson(eventUpdateRequestModel.value))

        val request = object: JsonObjectRequest(
            Request.Method.PATCH, url, updateData,
            {
                updateEventListener.onResponse()
            },
            {
                Toast.makeText(context, "Something wrong. Try again!", Toast.LENGTH_SHORT).show()
                updateEventListener.onError()
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