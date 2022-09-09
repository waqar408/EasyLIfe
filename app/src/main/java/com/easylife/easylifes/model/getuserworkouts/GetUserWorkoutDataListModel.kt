package com.easylife.easylifes.model.getuserworkouts

import com.google.gson.annotations.SerializedName

data class GetUserWorkoutDataListModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("trainer_id")
    var trainer_id : String,

    @SerializedName("user_id")
    var user_id : String,

    @SerializedName("title")
    var title : String,

    @SerializedName("user_workout_videos")
    var user_workout_videos : ArrayList<UserWorkoutVideoListModel>
)
