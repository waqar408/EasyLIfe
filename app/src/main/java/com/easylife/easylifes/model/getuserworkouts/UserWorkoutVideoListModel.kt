package com.easylife.easylifes.model.getuserworkouts

import com.google.gson.annotations.SerializedName

data class UserWorkoutVideoListModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("user_workout_id")
    var user_workout_id : String,

    @SerializedName("workout_id")
    var workout_id : String,

    @SerializedName("workout_category_id")
    var workout_category_id : String,

    @SerializedName("media_type")
    var media_type :String,

    @SerializedName("media")
    var media :String,

    @SerializedName("title")
    var title :String,

    @SerializedName("description")
    var description :String,

    @SerializedName("excercises")
    var excercises :String,

    @SerializedName("calories")
    var calories :String,

    @SerializedName("time")
    var time :String,

    @SerializedName("data")
    var data : ArrayList<UserWorkoutRepsDataModel>
)
