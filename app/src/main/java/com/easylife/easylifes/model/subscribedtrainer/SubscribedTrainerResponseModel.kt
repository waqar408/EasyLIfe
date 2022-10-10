package com.easylife.easylifes.model.subscribedtrainer

import com.google.gson.annotations.SerializedName

data class SubscribedTrainerResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ArrayList<SubscribedTrainerDataModel>
)