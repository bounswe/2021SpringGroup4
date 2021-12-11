package com.example.sportshub.legacy;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sportshub.event.model.EventModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventAPIConnection {

    public static final String QUERY_FOR_EVENT_LIST = "http://localhost:8000/api/events/";
    public static final String QUERY_FOR_CREATE_EVENT = "http://localhost:8000/api/events/";
    public static final String QUERY_FOR_EVENT_SEARCH_BY_LOCATION = "http://localhost:8000/api/search/event/location/";

    Context context;

    public EventAPIConnection(Context context) {
        this.context = context;
    }

    public interface EventListListener {
        void onError(String message);

        void onResponse(List<EventModel> eventList);
    }

    public void getEventList(EventListListener eventListListener){

        String url = QUERY_FOR_EVENT_LIST;

        List<EventModel> eventModels = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for(int i=0; i<response.length(); i++){
                                JSONObject one_event = response.getJSONObject(i);

                                EventModel one_event_model = new EventModel();
                                one_event_model.setId(one_event.getInt("id"));
                                one_event_model.setCreator(one_event.getString("creator"));
                                one_event_model.setTitle(one_event.getJSONObject("body").getString("title"));
                                one_event_model.setDescription(one_event.getJSONObject("body").getString("description"));
                                one_event_model.setDate(one_event.getJSONObject("body").getString("date"));
                                one_event_model.setTime(one_event.getJSONObject("body").getString("time"));
                                one_event_model.setDuration(one_event.getJSONObject("body").getString("duration"));
                                one_event_model.setLocation(one_event.getJSONObject("body").getString("location"));
                                one_event_model.setSportType(one_event.getJSONObject("body").getString("sportType"));
                                one_event_model.setMaxPlayers(one_event.getJSONObject("body").getInt("maxPlayers"));
                                eventModels.add(one_event_model);
                            }
                            eventListListener.onResponse(eventModels);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                eventListListener.onError("Something wrong");
            }
        });

        SingletonRequestQueue.getInstance(context).addToRequestQueue(request);

    }

    public interface CreateEventListener {
        void onError(String message);

        void onResponse(EventModel eventModel);
    }

    public void createEvent(String access, String title, String description, String location, Integer maxPlayers, String date, String time, String duration, String sportType, CreateEventListener createEventListener){

        String url = QUERY_FOR_CREATE_EVENT;

        JSONObject createEventData = new JSONObject();
        try{
            createEventData.put("title", title);
            createEventData.put("description", description);
            createEventData.put("location", location);
            createEventData.put("maxPlayers", maxPlayers);
            createEventData.put("date", date);
            createEventData.put("time", time);
            createEventData.put("duration", duration);
            createEventData.put("sportType", sportType);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, createEventData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            EventModel eventModel = new EventModel();
                            if(response.has("title")){
                                eventModel.setTitle(response.getString("title"));
                            }
                            if(response.has("location")){
                                eventModel.setLocation(response.getString("location"));
                            }
                            if(response.has("maxPlayers")){
                                eventModel.setMaxPlayers(response.getInt("maxPlayers"));
                            }
                            if(response.has("date")){
                                eventModel.setDate(response.getString("date"));
                            }
                            if(response.has("time")){
                                eventModel.setTime(response.getString("time"));
                            }
                            if(response.has("duration")){
                                eventModel.setDuration(response.getString("duration"));
                            }
                            if(response.has("body")){
                                eventModel.setId(response.getInt("id"));
                                eventModel.setCreator(response.getString("creator"));
                                eventModel.setTitle(response.getJSONObject("body").getString("title"));
                                eventModel.setDescription(response.getJSONObject("body").getString("description"));
                                eventModel.setDate(response.getJSONObject("body").getString("date"));
                                eventModel.setTime(response.getJSONObject("body").getString("time"));
                                eventModel.setDuration(response.getJSONObject("body").getString("duration"));
                                eventModel.setLocation(response.getJSONObject("body").getString("location"));
                                eventModel.setSportType(response.getJSONObject("body").getString("sportType"));
                                eventModel.setMaxPlayers(response.getJSONObject("body").getInt("maxPlayers"));
                            }
                            createEventListener.onResponse(eventModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                createEventListener.onError("Something wrong");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + access);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        SingletonRequestQueue.getInstance(context).addToRequestQueue(request);

    }

    public interface EventSearchByLocationListener {
        void onError(String message);

        void onResponse(List<EventModel> searchEventByLocationList);
    }

    public void searchEventByLocation(String location, Integer dist, EventSearchByLocationListener eventSearchByLocationListener){

        String url = QUERY_FOR_EVENT_SEARCH_BY_LOCATION;

        List<EventModel> eventModels = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray responseArray = new JSONArray(response);
                            for(int i=0; i<responseArray.length(); i++){
                                JSONObject one_event = responseArray.getJSONObject(i);

                                EventModel one_event_model = new EventModel();
                                one_event_model.setCreator(one_event.getString("owner"));
                                one_event_model.setId(one_event.getInt("id"));
                                one_event_model.setTitle(one_event.getString("title"));
                                one_event_model.setLocation(one_event.getString("location"));
                                eventModels.add(one_event_model);
                            }
                            eventSearchByLocationListener.onResponse(eventModels);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                eventSearchByLocationListener.onError("Something wrong");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("location", location);
                params.put("dist", dist.toString());
                return params;
            }
        };

        SingletonRequestQueue.getInstance(context).addToRequestQueue(request);

    }

}
