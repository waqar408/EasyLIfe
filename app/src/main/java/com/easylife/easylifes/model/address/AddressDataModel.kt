package com.easylife.easylifes.model.address

import com.google.gson.annotations.SerializedName

data class AddressDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("user_id")
    var user_id : String,

    @SerializedName("address_title")
    var address_title : String,

    @SerializedName("email")
    var email : String,

    @SerializedName("phone")
    var phone : String,

    @SerializedName("address")
    var address : String,

)