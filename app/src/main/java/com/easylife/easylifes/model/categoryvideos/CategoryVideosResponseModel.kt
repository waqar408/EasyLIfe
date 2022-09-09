package com.easylife.easylifes.model.categoryvideos

import com.google.gson.annotations.SerializedName

data class CategoryVideosResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ArrayList<CategoryVideoDataModel>
)

