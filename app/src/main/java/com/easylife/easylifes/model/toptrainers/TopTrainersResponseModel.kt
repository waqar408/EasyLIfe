package com.easylife.easylifes.model.toptrainers

import com.google.gson.annotations.SerializedName

data class TopTrainersResponseModel(
    @SerializedName("status")
    var status : Boolean,


    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : TopTrainerDataModel
)
