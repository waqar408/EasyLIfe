package com.ayurmitra.ayurmitra.model.reviews

import com.google.gson.annotations.SerializedName

data class ReviewDataModel(
    @SerializedName("current_page")
    var current_page : Int,

    @SerializedName("data")
    var reviewDetailDataModel : ArrayList<ReviewDetailDataModel>,

    @SerializedName("from")
    var from : Int,

    @SerializedName("last_page")
    var last_page : Int,

    @SerializedName("last_page_url")
    var last_page_url :String
)
