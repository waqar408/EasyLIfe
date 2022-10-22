package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.model.getuserworkouts.UserWorkoutRepsDataModel

class WorkoutDetailRepsAndRestAdapter(
    val context: Context,
    val list: ArrayList<UserWorkoutRepsDataModel>?,
) :
    RecyclerView.Adapter<WorkoutDetailRepsAndRestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_repsandrest, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: UserWorkoutRepsDataModel = list!!.get(position)
        holder.tvReps.text = model.reps
        holder.tvTimeReps.text = model.reps_minutes + ": "+model.reps_seconds
        holder.tvTimeRest.text = model.rest_minutes + ": "+model.rest_seconds

    }



    override fun getItemCount(): Int {
        return list!!.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvReps: TextView = itemView.findViewById(R.id.tvReps)
        val tvTimeReps: TextView = itemView.findViewById(R.id.tvTimeReps)
        val tvTimeRest: TextView = itemView.findViewById(R.id.tvTimeRest)

    }




}