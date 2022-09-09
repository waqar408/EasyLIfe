package com.easylife.easylifes.model.home

import com.google.gson.annotations.SerializedName

data class BannersDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("banner_image")
    var banner_image : String,


)
