package com.example.sportshub.event

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.TimeoutError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportshub.CustomRequest
import com.example.sportshub.R
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.equipment.model.EquipmentModel
import com.example.sportshub.event.model.EventModel
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject

class EventDetailViewModel : ViewModel() {
    val event : MutableLiveData<EventModel>? = MutableLiveData(EventModel())
    private val QUERY_FOR_SEARCH_BY_SPORT_TYPE = "${R.string.localBackend}/api/search/equipment/sport/"
    var equipments = MutableLiveData<MutableList<EquipmentModel>>()

    private val QUERY_FOR_ADD_COMMENT = "${R.string.localBackend}/api/events/comment/"
    private val QUERY_FOR_APPLY_EVENT = "${R.string.localBackend}/api/events/"
    private val QUERY_FOR_DELETE_EVENT = "${R.string.localBackend}/api/events/"

    fun addComment(context: Context, body: String, addCommentListener: AddCommentListener) {
        val url = QUERY_FOR_ADD_COMMENT

        val jsonString = "{\"body\":\"$body\", \"parent\":\"${event!!.value!!.id}\"}"
        val commentData = JSONObject(jsonString)

        val request = object: JsonObjectRequest(
            Request.Method.POST, url, commentData,
            {
                addCommentListener.onResponse()
            },
            {

                val statusCode: Int? = it.networkResponse?.statusCode

                if(statusCode == 400){
                    if(it.networkResponse?.data != null){
                        val response = JSONObject(String(it.networkResponse.data))
                        if(response.has("body")){
                            Toast.makeText(context, "Please do not leave your comment empty!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Could not add your comment. Please try again!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Could not add your comment. Please try again!", Toast.LENGTH_SHORT).show()
                    }
                } else{
                    Toast.makeText(context, "Could not add your comment. Please try again!", Toast.LENGTH_SHORT).show()
                }
                addCommentListener.onError()
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

    fun deleteEvent(context: Context, deleteEventListener: DeleteEventListener) {
        val url = QUERY_FOR_DELETE_EVENT + "${event!!.value!!.id}/"

        val request = object: JsonObjectRequest(
            Request.Method.DELETE, url, null,
            {
                deleteEventListener.onResponse()
            },
            {
                if(it.networkResponse == null){
                    deleteEventListener.onResponse()
                } else{
                    Toast.makeText(context,"Could not delete event. Please try again!", Toast.LENGTH_SHORT).show()
                    deleteEventListener.onError()
                }
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

    fun searchEquipmentsbySportType(sportType : String){
        val url = QUERY_FOR_SEARCH_BY_SPORT_TYPE
        val equipmentModels: MutableList<EquipmentModel> = mutableListOf()

        val req = JSONObject()
        req.put("sportType",sportType)

        val request = CustomRequest(
            Request.Method.POST, url, req,
            {
                try {
                    val gson = GsonBuilder().create()
                    for (i in 0 until it.length()) {
                        val equipment : EquipmentModel = gson.fromJson(it.getJSONObject(i).toString(), EquipmentModel::class.java)

                        equipmentModels.add(equipment)
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                equipments.value = equipmentModels
            },
            {
                equipments.value = equipmentModels
            }
        )
        SingletonRequestQueueProvider.getQueue().add(request)
    }

}