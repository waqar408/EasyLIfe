package com.easylife.easylifes.model.allworkouts

import com.google.gson.annotations.SerializedName

data class AllWorkoutsResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : AllWorkoutsDataModel
)