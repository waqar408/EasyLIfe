package com.easylife.easylifes.model.workoutdetial

import com.google.gson.annotations.SerializedName

data class UserWorkoutDetailResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ArrayList<UserWorkoutDetailDataModel>
)
