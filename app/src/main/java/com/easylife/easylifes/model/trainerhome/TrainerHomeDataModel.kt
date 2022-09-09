package com.easylife.easylifes.model.trainerhome

import com.easylife.easylifes.model.home.BannersDataModel
import com.google.gson.annotations.SerializedName

data class TrainerHomeDataModel(
    @SerializedName("banners")
    var banners : ArrayList<BannersDataModel>,


    @SerializedName("users")
    var users : ArrayList<TrainerUserDataModel>
)
