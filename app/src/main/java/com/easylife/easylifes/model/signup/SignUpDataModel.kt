package com.easylife.easylifes.model.signup

import androidx.annotation.StringRes
import com.google.gson.annotations.SerializedName

data class SignUpDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("facebook_id")
    var facebook_id : String,

    @SerializedName("google_id")
    var google_id :String,

    @SerializedName("name")
    var name : String,

    @SerializedName("username")
    var username : String,

    @SerializedName("email")
    var email : String,

    @SerializedName("profile_image")
    var profile_image : String,

    @SerializedName("country_code")
    var country_code : String,

    @SerializedName("phone")
    var phone : String,

    @SerializedName("gender")
    var gender : String,

    @SerializedName("age")
    var age : String,

    @SerializedName("weight")
    var weight : String,

    @SerializedName("weight_unit")
    var weight_unit :String,

    @SerializedName("height")
    var height : String,

    @SerializedName("height_unit")
    var height_unit : String,

    @SerializedName("your_goal")
    var your_goal : String,

    @SerializedName("how_to_achieve_goal")
    var how_to_achieve_goal : String,

    @SerializedName("current_fitness_level")
    var current_fitness_level : String,

    @SerializedName("interests")
    var interests : String,

    @SerializedName("newsletter")
    var newsletter : String,

    @SerializedName("text_message")
    var text_message :String,

    @SerializedName("phone_status")
    var phone_status : String,

    @SerializedName("currency")
    var currency : String,

    @SerializedName("language")
    var language : String,

    @SerializedName("experience")
    var experience : String,

    @SerializedName("average_rating")
    var average_rating :String,

    @SerializedName("location")
    var location : String,

    @SerializedName("address")
    var address : String,

    @SerializedName("is_profile_verified")
    var is_profile_verified :String,

    @SerializedName("type")
    var type : String,

    @SerializedName("is_profile_complete")
    var is_profile_complete : Boolean,

    @SerializedName("per_hour_rate")
    var per_hour_rate : String,

    @SerializedName("per_hour_rate_currency")
    var per_hour_rate_currency :String,

    @SerializedName("card_number")
    var card_number :String,

    @SerializedName("expiry_month")
    var expiry_month :String,

    @SerializedName("expiry_year")
    var expiry_year :String,

    @SerializedName("cvv")
    var cvv :String,

    @SerializedName("is_online")
    var is_online : String,

    @SerializedName("allow_notifications")
    var allow_notifications :String,

    @SerializedName("allow_admin_notifications")
    var allow_admin_notifications : String
)
