package com.easylife.easylifes.model.trainerportfolio

import com.google.gson.annotations.SerializedName

data class TrainerPortfolioResponseModel(
    @SerializedName("status")
    val status : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("data")
    var portfolioDataModel : ArrayList<PortfolioDataModel>
)