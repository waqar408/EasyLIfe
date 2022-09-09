package com.easylife.easylifes.model.forgotpassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordDataModel(
    @SerializedName("email")
    var email : String,

    @SerializedName("code")
    var code : Int
)
