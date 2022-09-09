package com.easylife.easylifes.model.getuserworkouts

import com.easylife.easylifes.model.chat.inbox.MessageDataListModel
import com.google.gson.annotations.SerializedName

data class GetUserWorkoutDataModel(
    @SerializedName("current_page")
    var current_page : Int,

    @SerializedName("data")
    var data :ArrayList<GetUserWorkoutDataListModel>,

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
