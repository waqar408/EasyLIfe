package com.easylife.easylifes.model.verifydata

import com.google.gson.annotations.SerializedName

data class VerficationDataModel(
    @SerializedName("country_code")
    var country_code : String,

    @SerializedName("phone")
    var phone : String,

    @SerializedName("otp")
    var otp : String
)

