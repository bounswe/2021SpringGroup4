package com.example.sportshub.event

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.event.model.EventModel
import org.json.JSONObject

class EventDetailViewModel : ViewModel() {
    val event : MutableLiveData<EventModel>? = MutableLiveData(EventModel())

    private val QUERY_FOR_APPLY_EVENT = "http://3.67.188.187:8000/api/events/"

    fun applyEvent(context: Context, applyEventListener: ApplyEventListener) {
        val url = QUERY_FOR_APPLY_EVENT + "${event!!.value!!.id}/"

        val jsonString = "{\"applicants\":{\"add\":[\"${SingletonRequestQueueProvider.getUsername()}\"]}}"
        val applyData = JSONObject(jsonString)

        val request = object: JsonObjectRequest(
            Request.Method.PATCH, url, applyData,
            {
                applyEventListener.onResponse()
            },
            {
                Toast.makeText(context,"Could not apply for event. Please try again!", Toast.LENGTH_SHORT).show()
                applyEventListener.onError()
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

    fun undoApplyEvent(context: Context, applyEventListener: ApplyEventListener) {
        val url = QUERY_FOR_APPLY_EVENT + "${event!!.value!!.id}/"

        val jsonString = "{\"applicants\":{\"remove\":[\"${SingletonRequestQueueProvider.getUsername()}\"]}}"
        val undoApplyData = JSONObject(jsonString)

        val request = object: JsonObjectRequest(
            Request.Method.PATCH, url, undoApplyData,
            {
                applyEventListener.onResponse()
            },
            {
                Toast.makeText(context,"Could not undo application for event. Please try again!", Toast.LENGTH_SHORT).show()
                applyEventListener.onError()
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