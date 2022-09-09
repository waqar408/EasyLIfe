package com.easylife.easylifes.model.workoutdetial

import com.google.gson.annotations.SerializedName

data class UserWorkoutDetailDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("trainer_id")
    var trainer_id : String,
    @SerializedName("user_id")
    var user_id : String,

    @SerializedName("workout_id")
    var workout_id : String,

    @SerializedName("reps")
    var reps : String,

    @SerializedName("reps_minutes")
    var reps_minutes : String,

    @SerializedName("reps_seconds")
    var reps_seconds :String,

    @SerializedName("workout_category_id")
    var workout_category_id :String,

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
    var calories:String,

    @SerializedName("time")
    var time :String
)