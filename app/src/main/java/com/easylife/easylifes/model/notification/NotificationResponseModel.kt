package com.easylife.easylifes.model.notification

import com.google.gson.annotations.SerializedName

data class NotificationResponseModel(
    @SerializedName("status")
    val status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ArrayList<NotificationDataModel>
)
