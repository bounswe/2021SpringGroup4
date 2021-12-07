package com.example.sportshub.ui.event

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.example.sportshub.backend_connection.SingletonRequestQueue
import com.example.sportshub.backend_connection.event.EventAPIConnection
import com.example.sportshub.backend_connection.event.EventModel
import com.example.sportshub.backend_connection.event.SportTypeModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class EventViewModel : ViewModel() {

    private val QUERY_FOR_EVENT_LIST = "http://3.67.188.187:8000/api/events"

    fun getAllEvents(context: Context, eventListListener: EventListListener) {
        val url = QUERY_FOR_EVENT_LIST
        var eventModels: MutableList<EventModel> = MutableList<EventModel>(0, init = {index -> EventModel()})

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                try {
                    for (i in 0 until it.length()) {
                        val one_event = it.getJSONObject(i)
                        val one_event_model = EventModel()
                        one_event_model.id = one_event.getInt("id")
                        one_event_model.creator = one_event.getString("creator")
                        one_event_model.title = one_event.getJSONObject("body").getString("title")
                        one_event_model.description = one_event.getJSONObject("body").getString("description")
                        one_event_model.date = one_event.getJSONObject("body").getString("date")
                        one_event_model.time = one_event.getJSONObject("body").getString("time")
                        one_event_model.duration = one_event.getJSONObject("body").getString("duration")
                        one_event_model.location = one_event.getJSONObject("body").getString("location")
                        one_event_model.sportType = one_event.getJSONObject("body").getString("sportType")
                        one_event_model.maxPlayers = one_event.getJSONObject("body").getInt("maxPlayers")
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
        SingletonRequestQueue.getInstance(context).addToRequestQueue(request)
    }

    fun searchByLocation(toString: String): MutableList<Int> {
        return MutableList(3,init = {index -> index})

    }
    //TODO Implement ViewModel
}