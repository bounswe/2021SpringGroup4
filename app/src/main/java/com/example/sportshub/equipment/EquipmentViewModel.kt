package com.example.sportshub.equipment

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.equipment.model.EquipmentModel
import com.example.sportshub.event.EventListListener
import com.example.sportshub.event.model.EventModel
import com.google.gson.GsonBuilder
import org.json.JSONException

class EquipmentViewModel : ViewModel() {
    private val QUERY_FOR_EQUIPMENT_LIST = "http://3.67.188.187:8000/api/equipment"
    var equipments = MutableLiveData<MutableList<EquipmentModel>>()
    fun getAllEquipments(){

            val url = QUERY_FOR_EQUIPMENT_LIST
            val equipmentModels: MutableList<EquipmentModel> = mutableListOf()

            val request = JsonArrayRequest(
                Request.Method.GET, url, null,
                {
                    try {
                        val gson = GsonBuilder().create()
                        for (i in 0 until it.length()) {
                            val equipment :EquipmentModel = gson.fromJson(it.getJSONObject(i).toString(), EquipmentModel::class.java)

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