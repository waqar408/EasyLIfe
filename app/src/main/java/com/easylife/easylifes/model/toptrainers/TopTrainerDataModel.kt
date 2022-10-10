package com.easylife.easylifes.model.toptrainers

import com.easylife.easylifes.model.home.BannersDataModel
import com.easylife.easylifes.model.subscribedtrainer.SubscribedTrainerDataModel
import com.google.gson.annotations.SerializedName

data class TopTrainerDataModel(
    @SerializedName("trending_workouts")
    var trending_workouts : ArrayList<BannersDataModel>,

    @SerializedName("top_trainers")
    var top_trainers : ArrayList<SubscribedTrainerDataModel>
)
