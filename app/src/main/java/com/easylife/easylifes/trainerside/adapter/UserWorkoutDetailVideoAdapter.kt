package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.allworkouts.WorkoutRepsAndRestModel
import com.easylife.easylifes.model.getuserworkouts.UserWorkoutRepsDataModel
import com.easylife.easylifes.model.getuserworkouts.UserWorkoutVideoListModel
import com.easylife.easylifes.trainerside.activities.clientdetail.AllWorkoutsActivity
import com.easylife.easylifes.utils.Utilities
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Response


class UserWorkoutDetailVideoAdapter(
    val context: Context,
    val list: ArrayList<UserWorkoutVideoListModel>,
    var mListener: onSelectedWorkoutClick

) :
    RecyclerView.Adapter<UserWorkoutDetailVideoAdapter.ViewHolder>() {
    private var viewPool = RecyclerView.RecycledViewPool()
    var user_WorkoutId = ""
    private var listForReps: ArrayList<WorkoutRepsAndRestModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_selected_workout_list, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: UserWorkoutVideoListModel = list.get(position)
        holder.tvName.text = model.title
        Glide.with(context).load(model.media).into(holder.imgProfile)
        holder.tvDescription.text = model.description
        var list: ArrayList<UserWorkoutRepsDataModel> = ArrayList()
        list = model.data
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.rvRepsAndRest.layoutManager = layoutManager
        val adapter = WorkoutDetailRepsAndRestAdapter(context, list)
        holder.rvRepsAndRest.adapter = adapter
        holder.rvRepsAndRest.setRecycledViewPool(viewPool)


        /*holder.itemView.setOnClickListener {
            mListener.onClickArea(position)
        }*/
        holder.layoutComplete.setOnClickListener {
            bottomsheetreps(
                position,
                model,
                list,
                holder.rvRepsAndRest,
                holder.tvName,
                holder.tvDescription,
                holder.imgProfile
            )

        }

    }


    private fun bottomsheetreps(
        position: Int,
        model: UserWorkoutVideoListModel,
        list: ArrayList<UserWorkoutRepsDataModel>,
        rv: RecyclerView,
        name: TextView,
        description: TextView,
        imgProfile: ShapeableImageView
    ) {
        val utilities = Utilities(context)
        val bottomSheetDialog: BottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottom_reps)
        bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val canelBtn = bottomSheetDialog.findViewById<RelativeLayout>(R.id.layout_backArrow)
        val edNoOfReps = bottomSheetDialog.findViewById<EditText>(R.id.edNoOfReps)
        val edRepsMinutes = bottomSheetDialog.findViewById<EditText>(R.id.edRepsMinutes)
        val edRepsSeconds = bottomSheetDialog.findViewById<EditText>(R.id.edRepsSeconds)
        val edRestMinutes = bottomSheetDialog.findViewById<EditText>(R.id.edRestMinutes)
        val edRestSeconds = bottomSheetDialog.findViewById<EditText>(R.id.edRestSeconds)
        val btnDone = bottomSheetDialog.findViewById<TextView>(R.id.btnDone)
        var reps = ""
        var repMinutes = ""
        var repsSeconds = ""
        var restMinute = ""
        var restSeconds = ""
        btnDone!!.setOnClickListener {
            reps = edNoOfReps!!.text.toString()
            restSeconds = edRestSeconds!!.text.toString()
            restMinute = edRestMinutes!!.text.toString()
            repMinutes = edRepsMinutes!!.text.toString()
            repsSeconds = edRepsSeconds!!.text.toString()
            if (reps.equals("")) {
                Toast.makeText(context, "Please Enter Reps", Toast.LENGTH_SHORT).show()
            } else if (repMinutes.equals("")) {
                Toast.makeText(context, "Please Enter Reps Minutes", Toast.LENGTH_SHORT).show()
            } else if (repsSeconds.equals("")) {
                Toast.makeText(context, "Please Enter Reps Seconds", Toast.LENGTH_SHORT).show()
            } else if (restMinute.equals("")) {
                Toast.makeText(context, "Please Enter Rest Minutes", Toast.LENGTH_SHORT).show()
            } else if (repsSeconds.equals("")) {
                Toast.makeText(context, "Please Enter Rest Seconds", Toast.LENGTH_SHORT).show()
            } else {

                val item = UserWorkoutRepsDataModel(
                    0,
                    "",
                    reps,
                    repMinutes,
                    repsSeconds,
                    restMinute,
                    restSeconds
                )
                var obj = model

                obj.data.add(item)
                val obj2 = UserWorkoutVideoListModel(
                    obj.id,
                    obj.user_workout_id,
                    obj.workout_id,
                    obj.workout_category_id,
                    obj.media_type,
                    obj.media,
                    obj.title,
                    obj.description,
                    obj.excercises,
                    obj.calories,
                    obj.time,
                    obj.data
                )
                obj = obj2

                name.text = obj2.title
                Glide.with(context).load(obj2.media).into(imgProfile)
                description.text = obj2.description
                user_WorkoutId = obj.user_workout_id
                val layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rv.layoutManager = layoutManager
                val adapter = WorkoutDetailRepsAndRestAdapter(context, obj.data)
                rv.adapter = adapter
                rv.setRecycledViewPool(viewPool)

                createWorkout(model, obj.data)


//                adapter.add(item)

                /*val list1 : ArrayList<WorkoutRepsAndRestModel> = ArrayList()
                list1.add(item)
                model.listReps?.add(item1)*/

                bottomSheetDialog.dismiss()
                Log.d("positionss", position.toString())
            }
        }

        canelBtn!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onSelectedWorkoutClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName);
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription);
        val imgProfile: ShapeableImageView = itemView.findViewById(R.id.imgProfile);
        val rvRepsAndRest: RecyclerView = itemView.findViewById(R.id.rvRepsAndRest)
        val layoutComplete: RelativeLayout = itemView.findViewById(R.id.layoutComplete)

    }

    interface onSelectedWorkoutClick {
        fun onClickArea(position: Int)
    }

    fun createWorkout(model: UserWorkoutVideoListModel, data: ArrayList<UserWorkoutRepsDataModel>) {
        val apiClient = ApiClient()
        var array1 = JsonArray()
        var array2 = JsonArray()
        var workoutid = ""
        for (i in 0 until data.size) {

            val id = data.get(i).id
            val user_workout_video_id = data.get(i).user_workout_video_id
            val rep = data.get(i).reps
            val repsMinutes = data.get(i).reps_minutes
            val reps_second = data.get(i).reps_seconds
            val res_minutes = data.get(i).rest_minutes
            val rest_Seconds = data.get(i).rest_seconds

            val obj1 = JsonObject()
            obj1.addProperty("id", 0)
            obj1.addProperty("user_workout_video_id", 0)
            obj1.addProperty("reps", rep.toInt())
            obj1.addProperty("reps_minutes", repsMinutes.toInt())
            obj1.addProperty("reps_seconds", reps_second.toInt())
            obj1.addProperty("rest_minutes", res_minutes.toInt())
            obj1.addProperty("rest_seconds", rest_Seconds.toInt())
            array1.add(obj1)


        }
        val jsonObject2 = JsonObject()
        jsonObject2.addProperty("workout_id", model.id)
        jsonObject2.add("data", array1)
        array2.add(jsonObject2)
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_workout_id", model.user_workout_id)
        jsonObject.add("workouts", array2)


        apiClient.getApiService().addMore(jsonObject)
            .enqueue(object : retrofit2.Callback<BaseResponse?> {
                override fun onResponse(
                    call: Call<BaseResponse?>,
                    response: Response<BaseResponse?>
                ) {
                    if (response.body() != null) {
                        val status = response.body()!!.status
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse?>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                    // showToast(t.message.toString())

                }
            })


    }

}