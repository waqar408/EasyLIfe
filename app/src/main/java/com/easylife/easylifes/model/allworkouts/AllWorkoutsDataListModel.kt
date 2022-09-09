package com.easylife.easylifes.model.allworkouts

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class AllWorkoutsDataListModel(

    var id : Int,
    var workout_category_id : String,
    var media_type : String,
    var media : String,
    var title: String,
    var description : String,
    var excercises : String,
    var calories : String,
    var time : String,
    var listReps :  ArrayList<WorkoutRepsAndRestModel> = ArrayList()

)  {
    var isChecked: Boolean = false
}