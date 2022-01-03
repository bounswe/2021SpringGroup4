package com.example.sportshub.equipment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateEquipmentRequestModel(
    var title : String = "",
    var description : String = "",
    var location : String = "",
    var imageUrl : String = "",
    var contact : String = "",
    var sportType : String = ""
):Parcelable
