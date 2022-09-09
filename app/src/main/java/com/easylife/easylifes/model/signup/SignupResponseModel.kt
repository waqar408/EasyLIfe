package com.easylife.easylifes.model.signup

import com.google.gson.annotations.SerializedName

data class SignupResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : SignUpDataModel
)
