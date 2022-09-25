package com.easylife.easylifes.model.mealplan

import com.google.gson.annotations.SerializedName

data class MealTimeDataModel(
    var isChecked : Boolean,

    @SerializedName("id")
    var id : Int,

    @SerializedName("meal_time")
    var meal_time : String,

    @SerializedName("user_meal_plan_id")
    var user_meal_plan_id : String,

    @SerializedName("foods")
    var foods :  ArrayList<FoodDataModel>
)
