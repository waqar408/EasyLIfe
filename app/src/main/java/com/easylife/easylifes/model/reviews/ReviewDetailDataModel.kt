package com.ayurmitra.ayurmitra.model.reviews

import com.google.android.datatransport.cct.StringMerger
import com.google.gson.annotations.SerializedName

data class ReviewDetailDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("user_id")
    var user_id : String,

    @SerializedName("trainer_id")
    var trainer_id :String,

    @SerializedName("rating_stars")
    var rating_stars :String,

    @SerializedName("review")
    var review :String,

    @SerializedName("review_time")
    var review_time : Int,

    @SerializedName("username")
    var username :String,

    @SerializedName("profile_image")
    var profile_image :String
)
