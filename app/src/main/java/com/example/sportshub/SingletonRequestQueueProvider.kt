package com.example.sportshub

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object SingletonRequestQueueProvider {
    private lateinit var requestQueue : RequestQueue
    fun createQueue(context: Context){
        requestQueue = Volley.newRequestQueue(context)
    }
    fun getQueue(): RequestQueue{
        return requestQueue
    }
}