package com.easylife.easylifes.model.chat.inbox

import com.google.gson.annotations.SerializedName

data class OtherUserDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("name")
    var name : String,

    @SerializedName("username")
    var username :String,

    @SerializedName("profile_image")
    var profile_image : String,

    @SerializedName("type")
    var type :String
)
