package com.easylife.easylifes.model.subscribedtrainer

import com.google.gson.annotations.SerializedName

data class SubscribedTrainerDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("name")
    var name : String,

    @SerializedName("username")
    var username : String,

    @SerializedName("experience")
    var experience : String,

    @SerializedName("profile_image")
    var profile_image : String,

    @SerializedName("specialization")
    var specialization :String,

    @SerializedName("average_rating")
    var average_rating : String,

    @SerializedName("workout_assigned")
    var workout_assigned : Boolean,

    @SerializedName("meal_plan_assigned")
    var meal_plan_assigned : Boolean,

    @SerializedName("package_name")
    var package_name : String,

    @SerializedName("package_price")
    var package_price :String,

    @SerializedName("price_currency")
    var price_currency : String,

    @SerializedName("validity_days")
    var validity_days :String,

    @SerializedName("remaining_days")
    var remaining_days : Int,

    @SerializedName("is_expired")
    var is_expired : Boolean
)
