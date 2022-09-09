package com.easylife.easylifes.model.chat.messenger

import com.google.gson.annotations.SerializedName

data  class MessengerDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("from_id")
    var from_id : String,

    @SerializedName("to_id")
    var to_id : String,

    @SerializedName("text")
    var text : String,

    @SerializedName("media")
    var media : String,

    @SerializedName("media_type")
    var media_type:  String,

    @SerializedName("time")
    var time : Int,

    @SerializedName("seen")
    var seen : String,

    @SerializedName("other_user_id")
    var other_user_id : Int,

    @SerializedName("other_user_name")
    var other_user_name : String,

    @SerializedName("other_user_avatar")
    var other_user_avatar :String,

    @SerializedName("other_user_type")
    var other_user_type : String,

    @SerializedName("unread_messages")
    var unread_messages :Int
)