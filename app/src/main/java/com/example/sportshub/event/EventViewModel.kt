package com.example.sportshub.event

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.example.sportshub.CustomRequest
import com.example.sportshub.R
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.event.model.EventModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class EventViewModel : ViewModel() {

    private val QUERY_FOR_EVENT_LIST = "${R.string.localBackend}/api/events"
    private val QUERY_FOR_SEARCH_BY_CREATOR = "${R.string.localBackend}/api/search/event/owner/"
    private val QUERY_FOR_SEARCH_BY_SPORT_TYPE = "${R.string.localBackend}/api/search/event/sport/"
    private val QUERY_FOR_SEARCH_BY_SKILL_LEVEL = "${R.string.localBackend}/api/search/event/skill/"
    var eventList : MutableLiveData<List<EventModel>> = MutableLiveData<List<EventModel>>()


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
                        one_event_model.skillLevel = one_event.getJSONObject("body").getString("skill_level")
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
    fun searchByCreator(username : String) {
        val url = QUERY_FOR_SEARCH_BY_CREATOR
        val eventModels: MutableList<EventModel> = mutableListOf()
        val req = JSONObject()
        req.put("username",username)

        val request = CustomRequest(
            Request.Method.POST, url, req,
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
                eventList.value = eventModels
            },
            {
                eventList.value = eventModels
            }
        )
        SingletonRequestQueueProvider.getQueue().add(request)

    }
    fun searchByType(sportType : String){
        val url = QUERY_FOR_SEARCH_BY_SPORT_TYPE
        val eventModels: MutableList<EventModel> = mutableListOf()

        val req = JSONObject()
        req.put("sportType",sportType)

        val request = CustomRequest(
            Request.Method.POST, url, req,
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
                eventList.value = eventModels
            },
            {
                eventList.value = eventModels
            }
        )
        SingletonRequestQueueProvider.getQueue().add(request)

    }
    fun searchByLevel(skill_level : String){
        val url = QUERY_FOR_SEARCH_BY_SKILL_LEVEL
        val eventModels: MutableList<EventModel> = mutableListOf()

        val req = JSONObject()
        req.put("skill_level",skill_level)

        val request = CustomRequest(
            Request.Method.POST, url, req,
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
                eventList.value = eventModels
            },
            {
                eventList.value = eventModels
            }
        )
        SingletonRequestQueueProvider.getQueue().add(request)

    }

}