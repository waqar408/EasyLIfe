package com.easylife.easylifes.model.mealplan

import com.google.gson.annotations.SerializedName

data class FoodDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("meal_plan_id")
    var meal_plan_id :String,


    @SerializedName("meal_time_id")
    var meal_time_id : String,

    @SerializedName("meal_id")
    var meal_id : String,

    @SerializedName("serving_id")
    var serving_id :String,


    @SerializedName("serving_quantity")
    var serving_quantity :String,


    @SerializedName("food_details")
    var food_details : FoodDetailDataModel
)
