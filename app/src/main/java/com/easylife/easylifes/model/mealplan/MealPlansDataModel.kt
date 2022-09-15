package com.easylife.easylifes.model.mealplan

import com.google.gson.annotations.SerializedName

data class MealPlansDataModel(
    @SerializedName("id")
    var id :Int,

    @SerializedName("user_id")
    var user_id :String,

    @SerializedName("trainer_id")
    var trainer_id :String,

    @SerializedName("title")
    var title :String,

    @SerializedName("calorie_target")
    var calorie_target :String,

    @SerializedName("macro_type")
    var macro_type :String,

    @SerializedName("protien")
    var protien :String,

    @SerializedName("carbs")
    var carbs :String,

    @SerializedName("fat")
    var fat : String,

    @SerializedName("fibre")
    var fibre :String,

    @SerializedName("sodium")
    var sodium : String,

    @SerializedName("sugar")
    var sugar :String,

    @SerializedName("meal_times")
    var meal_times : ArrayList<MealTimeDataModel>
)
