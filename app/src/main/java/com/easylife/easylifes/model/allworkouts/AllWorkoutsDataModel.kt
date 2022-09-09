package com.easylife.easylifes.model.allworkouts

import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutDataListModel
import com.google.gson.annotations.SerializedName

data class AllWorkoutsDataModel(
    @SerializedName("current_page")
    var current_page : Int,

    @SerializedName("data")
    var data :ArrayList<AllWorkoutsDataListModel>,

    @SerializedName("first_page_url")
    var first_page_url :String,

    @SerializedName("from")
    var from : Int,

    @SerializedName("last_page")
    var last_page : Int,

    @SerializedName("last_page_url")
    var last_page_url : String,

    @SerializedName("next_page_url")
    var next_page_url : String,

    @SerializedName("path")
    var path : String,
)
