package com.example.sportshub.equipment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EquipmentModel(var id:Int,
                          var owner : String,
                          var title : String,
                          var description : String,
                          var lcoation : String,
                          var image_url : String,
                          var contact : String,
                          var sportType : String
                          ): Parcelable
