package com.easylife.easylifes.model.trainerdetail

import com.google.gson.annotations.SerializedName

data class ReviewsDataModel(
    @SerializedName("current_page")
    var current_page : Int,

    @SerializedName("data")
    var data : ArrayList<ReviewDataListModel>,

    @SerializedName("first_page_url")
    var first_page_url : String,

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
