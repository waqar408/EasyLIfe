package com.easylife.easylifes.model.trainerhome

import com.google.gson.annotations.SerializedName

data class TrainerHomeResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data :TrainerHomeDataModel
)