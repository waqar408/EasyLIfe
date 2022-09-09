package com.easylife.easylifes.model.getuserworkouts

import com.google.gson.annotations.SerializedName

data class UserWorkoutRepsDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("user_workout_video_id")
    var user_workout_video_id :String,

    @SerializedName("reps")
    var reps :String,

    @SerializedName("reps_minutes")
    var reps_minutes :String,

    @SerializedName("reps_seconds")
    var reps_seconds :String,

    @SerializedName("rest_minutes")
    var rest_minutes :String,


    @SerializedName("rest_seconds")
    var rest_seconds :String
)
