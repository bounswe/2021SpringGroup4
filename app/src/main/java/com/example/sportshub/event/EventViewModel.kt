package com.example.sportshub.event

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.event.model.EventModel
import com.google.gson.GsonBuilder
import org.json.JSONException

class EventViewModel : ViewModel() {

    private val QUERY_FOR_EVENT_LIST = "http://3.67.188.187:8000/api/events"
    var eventList = listOf<EventModel>()
    fun getAllEvents(context: Context, eventListListener: EventListListener) {
        val url = QUERY_FOR_EVENT_LIST
        val eventModels: MutableList<EventModel> = mutableListOf()

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                try {
                    val gson = GsonBuilder().create()
                    for (i in 0 until it.length()) {
                        val one_event = it.getJSONObject(i)
                        val one_event_model = gson.fromJson(one_event.getJSONObject("body").toString(), EventModel::class.java)
                        one_event_model.id = one_event.getInt("id")
                        one_event_model.creator = one_event.getString("creator")
                        eventModels.add(one_event_model)
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                eventListListener.onResponse(eventModels)
            },
            {
                Toast.makeText(context,"Something wrong", Toast.LENGTH_SHORT).show()
                eventListListener.onError()
            }
        )
        SingletonRequestQueueProvider.getQueue().add(request)
    }
    fun searchByCreator(username : String): List<EventModel> {
        return listOf<EventModel>()
    }
    fun searchByType(sprotType : String): List<EventModel>{
        return listOf<EventModel>()
    }
    fun searchByLevel(skill_level : String): List<EventModel>{
        return listOf<EventModel>()
    }

    //TODO Implement ViewModel
}