package com.easylife.easylifes.model.trainerdetail

import com.google.gson.annotations.SerializedName

data class ReviewDataListModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("user_id")
    var user_id : String,

    @SerializedName("trainer_id")
    var trainer_id : String,

    @SerializedName("rating_stars")
    var rating_stars : String,

    @SerializedName("review")
    var review : String,

    @SerializedName("review_time")
    var review_time : Int,

    @SerializedName("username")
    var username : String,

    @SerializedName("profile_image")
    var profile_image : String,

    @SerializedName("is_favourite")
    var is_favourite : Boolean,

    @SerializedName("is_liked")
    var is_liked : Boolean,

    @SerializedName("is_commented")
    var is_commented : Boolean
)
