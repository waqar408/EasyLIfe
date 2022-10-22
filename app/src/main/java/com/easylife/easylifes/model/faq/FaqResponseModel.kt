package com.easylife.easylifes.model.faq

import com.google.gson.annotations.SerializedName

data class FaqResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ArrayList<FaqDataModel>
)
