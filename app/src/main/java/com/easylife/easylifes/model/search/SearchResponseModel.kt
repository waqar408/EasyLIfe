package com.easylife.easylifes.model.search

import com.google.gson.annotations.SerializedName

data class SearchResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message :String,

    @SerializedName("data")
    var data : ArrayList<SearchDataModel>
)
