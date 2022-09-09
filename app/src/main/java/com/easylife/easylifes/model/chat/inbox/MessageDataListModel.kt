package com.easylife.easylifes.model.chat.inbox

import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName

data class MessageDataListModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("from_id")
    var from_id : String,

    @SerializedName("to_id")
    var to_id :String,

    @SerializedName("text")
    var text : String,

    @SerializedName("message_type")
    var message_type : String,

    @SerializedName("media")
    var media : String,

    @SerializedName("media_type")
    var media_type : String,

    @SerializedName("time")
    var time : Int,

    @SerializedName("seen")
    var seen : Int,

    @SerializedName("other_user")
    var other_user : OtherUserDataModel
)
