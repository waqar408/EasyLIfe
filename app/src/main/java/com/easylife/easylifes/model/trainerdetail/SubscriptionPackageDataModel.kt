package com.easylife.easylifes.model.trainerdetail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class SubscriptionPackageDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("trainer_id")
    var trainer_id : String,

    @SerializedName("package_name")
    var package_name : String,

    @SerializedName("package_price")
    var package_price :String,

    @SerializedName("price_currency")
    var price_currency :String,

    @SerializedName("validity_days")
    var validity_days :String,

    @SerializedName("is_subscribed")
    var is_subscribed :Boolean
) : Parcelable
