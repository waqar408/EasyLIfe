package com.ayurmitra.ayurmitra.model.reviews

import com.google.gson.annotations.SerializedName

data class ReviewResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ReviewDataModel
)
