package com.easylife.easylifes.model.userworkoutcategories

import com.google.gson.annotations.SerializedName

data class UserCategoryResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var data : ArrayList<UserCategoryDataModel>
)
