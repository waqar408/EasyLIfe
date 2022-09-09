package com.easylife.easylifes.model.categoryvideos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CategoryVideoDataModel(
    @SerializedName("id")
    var id : Int,

    @SerializedName("media_type")
    var media_type : String,

    @SerializedName("media")
    var media : String,

    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description : String,
    @SerializedName("excercises")
    var excercises : String,

    @SerializedName("calories")
    var calories : String,

    @SerializedName("time")
    var time : String,


    var reps : Int = 0,
    var reps_minutes : Int= 0,
    var reps_seconds: Int = 0,
) : Parcelable{
    var isChecked: Boolean = false

}
