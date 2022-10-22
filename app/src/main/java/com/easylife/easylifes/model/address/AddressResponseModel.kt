package com.easylife.easylifes.model.address

import com.google.gson.annotations.SerializedName

data class AddressResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data :  AddressDataModel
)
