package com.easylife.easylifes.model.search

import com.google.gson.annotations.SerializedName

data class ServingDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("meal_id")
    var meal_id : String,

    @SerializedName("serving_size")
    var serving_size :String
)
