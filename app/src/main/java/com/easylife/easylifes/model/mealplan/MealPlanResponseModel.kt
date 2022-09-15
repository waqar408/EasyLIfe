package com.easylife.easylifes.model.mealplan

import com.google.gson.annotations.SerializedName

data class MealPlanResponseModel(
    @SerializedName("status")
    var status : Boolean,

    @SerializedName("message")
    var message :String,

    @SerializedName("data")
    var data : ArrayList<MealPlansDataModel>
)
