package com.easylife.easylifes.model.forgotpassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ForgotPasswordDataModel
)
