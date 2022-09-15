package com.easylife.easylifes.model.search

import com.google.gson.annotations.SerializedName

data class SearchDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("meal_title")
    var meal_title : String,

    @SerializedName("meal_calories")
    var meal_calories :String,

    @SerializedName("meal_protien")
    var meal_protien :String,

    @SerializedName("meal_carbs")
    var meal_carbs :String,

    @SerializedName("meal_fat")
    var meal_fat :String,

    @SerializedName("meal_fibre")
    var meal_fibre :String,

    @SerializedName("meal_sodium")
    var meal_sodium : String,

    @SerializedName("meal_sugar")
    var meal_sugar :String,

    @SerializedName("servings")
    var servings : ArrayList<ServingDataModel>
){
    var isChecked: Boolean = false
}
