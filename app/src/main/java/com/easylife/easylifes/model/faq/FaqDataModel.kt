package com.easylife.easylifes.model.faq

import com.google.gson.annotations.SerializedName

data class FaqDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("question")
    var question : String,


    @SerializedName("answer")
    var answer : String
)
