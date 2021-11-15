package com.example.sportshub.backend_connection.event;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sportshub.backend_connection.SingletonRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventAPIConnection {

    public static final String QUERY_FOR_EVENT_LIST = "http://localhost:8000/api/events/";
    public static final String QUERY_FOR_CREATE_EVENT = "http://localhost:8000/api/events/";

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
                                SportTypeModel sportTypeModel = new SportTypeModel();
                                sportTypeModel.setName(one_event.getJSONObject("body").getString("sportType"));
                                one_event_model.setSportType(sportTypeModel);
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

    public void createEvent(String title, String description, String location, Integer maxPlayers, String date, String time, String duration, String sportType, CreateEventListener createEventListener){

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
                                SportTypeModel sportTypeModel = new SportTypeModel();
                                sportTypeModel.setName(response.getJSONObject("body").getString("sportType"));
                                eventModel.setSportType(sportTypeModel);
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
        });

        SingletonRequestQueue.getInstance(context).addToRequestQueue(request);

    }

}
