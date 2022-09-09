package com.easylife.easylifes.model.chat.inbox

import com.google.gson.annotations.SerializedName

data class MessageDataModel(
    @SerializedName("current_page")
    var current_page : Int,

    @SerializedName("data")
    var data :ArrayList<MessageDataListModel?>,

    @SerializedName("first_page_url")
    var first_page_url :String,

    @SerializedName("from")
    var from : Int,

    @SerializedName("last_page")
    var last_page : Int,

    @SerializedName("last_page_url")
    var last_page_url : String,

    @SerializedName("next_page_url")
    var next_page_url : String,

    @SerializedName("path")
    var path : String,
)
