package com.easylife.easylifes.model.trainerportfolio

import com.google.gson.annotations.SerializedName

data class PortfolioDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("video")
    var video : String
)
