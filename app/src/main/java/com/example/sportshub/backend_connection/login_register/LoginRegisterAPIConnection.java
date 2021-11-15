package com.example.sportshub.backend_connection.login_register;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sportshub.backend_connection.SingletonRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRegisterAPIConnection {

    public static final String QUERY_FOR_REGISTER = "http://localhost:8000/api/auth/register/";
    public static final String QUERY_FOR_LOGIN = "http://localhost:8000/api/auth/login/";

    Context context;

    public LoginRegisterAPIConnection(Context context) { this.context = context; }

    public interface RegisterListener {
        void onError(String message);

        void onResponse(RegisterModel registerModel);
    }

    public void register(String email, String username, String password, RegisterListener registerListener){

        String url = QUERY_FOR_REGISTER;

        JSONObject registerData = new JSONObject();
        try{
            registerData.put("email", email);
            registerData.put("username", username);
            registerData.put("password", password);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, registerData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            RegisterModel registerModel = new RegisterModel();
                            if(response.has("email")){
                                registerModel.setEmail(response.getString("email"));
                            }
                            if(response.has("username")){
                                registerModel.setUsername(response.getString("username"));
                            }
                            registerListener.onResponse(registerModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                registerListener.onError("Something wrong");
            }
        });

        SingletonRequestQueue.getInstance(context).addToRequestQueue(request);

    }

    public interface LoginListener {
        void onError(String message);

        void onResponse(LoginResponseModel loginResponseModel);
    }

    public void login(String username, String password, LoginListener loginListener){

        String url = QUERY_FOR_LOGIN;

        JSONObject loginData = new JSONObject();
        try{
            loginData.put("username", username);
            loginData.put("password", password);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, loginData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            LoginResponseModel loginResponseModel = new LoginResponseModel();
                            if(response.has("refresh") || response.has("access")) {
                                loginResponseModel.setRefresh(response.getString("refresh"));
                                loginResponseModel.setAccess(response.getString("access"));
                            } else if(response.has("detail")) {
                                loginResponseModel.setDetail(response.getString("detail"));
                            }
                            loginListener.onResponse(loginResponseModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginListener.onError("Something wrong");
            }
        });

        SingletonRequestQueue.getInstance(context).addToRequestQueue(request);

    }

}


