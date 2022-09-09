package com.easylife.easylifes.model.getuserworkouts

import com.google.gson.annotations.SerializedName

data class GetUserWorkoutsResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : GetUserWorkoutDataModel

)
