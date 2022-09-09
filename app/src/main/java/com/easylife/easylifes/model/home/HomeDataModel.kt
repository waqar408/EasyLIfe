package com.easylife.easylifes.model.home

import com.google.gson.annotations.SerializedName

data class HomeDataModel(
    @SerializedName("banners")
    var banners : ArrayList<BannersDataModel>,

    @SerializedName("categories")
    var categories : ArrayList<CategoriesDataModel>
)