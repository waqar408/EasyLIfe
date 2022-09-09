package com.easylife.easylifes.model.chat.messenger

import com.google.gson.annotations.SerializedName

data class MessengerResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ArrayList<MessengerDataModel>
)
