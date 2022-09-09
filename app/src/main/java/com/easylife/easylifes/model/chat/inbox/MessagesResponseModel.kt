package com.easylife.easylifes.model.chat.inbox

import com.google.gson.annotations.SerializedName

data class MessagesResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : MessageDataModel
)
