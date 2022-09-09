package com.easylife.easylifes.model.allclients

import com.easylife.easylifes.model.trainerhome.TrainerUserDataModel
import com.google.gson.annotations.SerializedName

data class AllClientsResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ArrayList<TrainerUserDataModel>
)
