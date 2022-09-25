package com.easylife.easylifes.model.mealtimes

import com.google.gson.annotations.SerializedName

data class MealTimesDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("user_meal_plan_id")
    var user_meal_plan_id : String,

    @SerializedName("meal_time")
    var meal_time :String,

    @SerializedName("foods")
    var foods : ArrayList<MealTimeFoodsDataModel>
)
