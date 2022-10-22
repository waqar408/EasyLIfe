package com.easylife.easylifes.model.notification

import com.google.gson.annotations.SerializedName

data class NotificationDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("notification_from")
    var notification_from : String,

    @SerializedName("notification_to")
    var notification_to :String,

    @SerializedName("notification")
    var notification : String,

    @SerializedName("notification_time")
    var notification_time : Int,

    @SerializedName("notification_type")
    var notification_type :String,

    @SerializedName("notification_from_name")
    var notification_from_name : String,

    @SerializedName("notification_from_image")
    var notification_from_image :String,

    @SerializedName("meal_plan_id")
    var meal_plan_id : String? = null,

    @SerializedName("workout_id")
    var workout_id : String? = null
)
