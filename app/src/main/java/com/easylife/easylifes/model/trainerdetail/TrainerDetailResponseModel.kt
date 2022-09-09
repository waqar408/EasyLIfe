package com.easylife.easylifes.model.trainerdetail

import com.google.gson.annotations.SerializedName

data class TrainerDetailResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : TrainerDetailDataModel
)
