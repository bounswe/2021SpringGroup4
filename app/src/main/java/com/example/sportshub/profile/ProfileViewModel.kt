package com.example.sportshub.profile

import android.content.Context
import android.provider.ContactsContract
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.event.AddCommentListener
import com.example.sportshub.event.model.EventModel
import com.example.sportshub.profile.model.BadgeModel
import com.example.sportshub.profile.model.ProfileModel
import com.google.gson.GsonBuilder
import org.json.JSONException

class ProfileViewModel : ViewModel() {

    private val QUERY_FOR_GET_PROFILE = "http://3.67.188.187:8000/api/profiles/"
    private val QUERY_FOR_GET_EVENT_DETAIL = "http://3.67.188.187:8000/api/events/"

    fun getProfile(context: Context, username: String, getProfileListener: GetProfileListener){
        val url = QUERY_FOR_GET_PROFILE + "$username/"
        var profile_model = ProfileModel()

        val request = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                try {
                    val gson = GsonBuilder().create()
                    profile_model = gson.fromJson(it.toString(), ProfileModel::class.java)
                    for (i in 0 until it.getJSONArray("badges").length()) {
                        val one_badge = it.getJSONArray("badges").getJSONObject(i)
                        val one_badge_model = gson.fromJson(one_badge.toString(), BadgeModel::class.java)
                        one_badge_model.event_id = one_badge.getJSONObject("event").getInt("id")
                        profile_model.badges_list.add(one_badge_model)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                getProfileListener.onResponse(profile_model)
            },
            {
                val statusCode: Int? = it.networkResponse?.statusCode
                getProfileListener.onError(statusCode)
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

    fun getEventDetail(context: Context, eventId: Int, getEventDetailListener: GetEventDetailListener){
        val url = QUERY_FOR_GET_EVENT_DETAIL + "$eventId/"
        var event_model = EventModel()

        val request = object: JsonObjectRequest(
            Request.Method.GET, url, null,
            {
                try {
                    val gson = GsonBuilder().create()
                    event_model = gson.fromJson(it.getJSONObject("body").toString(), EventModel::class.java)
                    event_model.id = it.getInt("id")
                    event_model.creator = it.getString("creator")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                getEventDetailListener.onResponse(event_model)
            },
            {
                getEventDetailListener.onError()
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