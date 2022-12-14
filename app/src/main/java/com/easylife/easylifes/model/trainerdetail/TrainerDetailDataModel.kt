package com.easylife.easylifes.model.trainerdetail

import com.google.gson.annotations.SerializedName

data class TrainerDetailDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("name")
    var name : String,

    @SerializedName("username")
    var username : String,

    @SerializedName("profile_image")
    var profile_image : String,

    @SerializedName("specialization")
    var specialization :String,

    @SerializedName("experience")
    var experience : String,

    @SerializedName("completed_workouts")
    var completed_workouts : String,

    @SerializedName("active_clients")
    var active_clients : String,

    @SerializedName("average_rating")
    var average_rating : String,

    @SerializedName("total_reviews")
    var total_reviews : Int,

    @SerializedName("workout_assigned")
    var workout_assigned : Boolean,


    @SerializedName("meal_plan_assigned")
    var meal_plan_assigned : Boolean,

    @SerializedName("videos")
    var videos : ArrayList<VideoListDataModel>,


    @SerializedName("is_subscribed")
    var is_subscribed : Int,


    @SerializedName("subscription_packages")
    var subscription_packages : ArrayList<SubscriptionPackageDataModel>,

    @SerializedName("five_stars")
    var five_stars : Int,

    @SerializedName("four_stars")
    var four_stars : Int,

    @SerializedName("three_stars")
    var three_stars : Int,

    @SerializedName("reviews")
    var reviews : ReviewsDataModel
)
