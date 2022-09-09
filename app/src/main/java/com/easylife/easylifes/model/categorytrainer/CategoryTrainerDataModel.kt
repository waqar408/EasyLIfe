package com.easylife.easylifes.model.categorytrainer

import com.google.gson.annotations.SerializedName

data class CategoryTrainerDataModel(
    @SerializedName("id")
    var id : Int,
    @SerializedName("name")
    var name : String,

    @SerializedName("username")
    var username : String,

    @SerializedName("profile_image")
    var profile_image : String,

    @SerializedName("specialization")
    var specialization : String,

    @SerializedName("experience")
    var experience : String,

    @SerializedName("average_rating")
    var average_rating : String,

    @SerializedName("per_hour_rate")
    var per_hour_rate : String,

    @SerializedName("per_hour_rate_currency")
    var per_hour_rate_currency : String,

    @SerializedName("is_favourite")
    var is_favourite : Boolean
)
