package com.easylife.easylifes.model.home

import com.google.gson.annotations.SerializedName

data class CategoriesDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("category_name")
    var category_name : String,

    @SerializedName("category_image")
    var category_image : String
)
