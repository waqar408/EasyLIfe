package com.easylife.easylifes.model.mealtimes

import com.google.gson.annotations.SerializedName

data class MealTimesResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ArrayList<MealTimesDataModel>
)
