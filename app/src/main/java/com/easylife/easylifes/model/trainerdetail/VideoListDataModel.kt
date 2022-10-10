package com.easylife.easylifes.model.trainerdetail

import com.google.gson.annotations.SerializedName

data class VideoListDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("video")
    var video : String,

)
