package com.easylife.easylifes.model.home

import com.google.gson.annotations.SerializedName

data class BannersDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("banner_image")
    var banner_image : String,

    @SerializedName("workout_banner")
    var workout_banner : String,

    @SerializedName("website_url")
    var website_url : String
)
