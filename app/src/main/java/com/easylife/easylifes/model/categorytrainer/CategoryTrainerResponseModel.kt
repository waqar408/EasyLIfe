package com.easylife.easylifes.model.categorytrainer

import com.google.gson.annotations.SerializedName

data class CategoryTrainerResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ArrayList<CategoryTrainerDataModel>
)