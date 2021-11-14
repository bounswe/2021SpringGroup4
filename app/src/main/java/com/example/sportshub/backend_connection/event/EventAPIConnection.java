package com.example.sportshub.backend_connection.event;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sportshub.backend_connection.SingletonRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventAPIConnection {

    public static final String QUERY_FOR_EVENT_LIST = "http://localhost:8000/api/events/";

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

}
