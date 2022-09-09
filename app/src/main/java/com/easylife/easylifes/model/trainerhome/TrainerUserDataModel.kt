package com.easylife.easylifes.model.trainerhome

import com.google.gson.annotations.SerializedName

data class TrainerUserDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("name")
    var name : String,

    @SerializedName("username")
    var username : String,

    @SerializedName("profile_image")
    var profile_image : String,

    @SerializedName("gender")
    var gender : String,

    @SerializedName("age")
    var age :String,

    @SerializedName("weight")
    var weight : String,

    @SerializedName("weight_unit")
    var weight_unit : String
)
