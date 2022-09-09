package com.easylife.easylifes.model.home

import com.google.gson.annotations.SerializedName

data class HomeResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : HomeDataModel
)
