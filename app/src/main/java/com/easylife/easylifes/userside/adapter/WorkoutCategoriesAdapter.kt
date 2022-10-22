package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.workout.FullImageWorkoutActivity
import com.easylife.easylifes.model.JobsDataModel
import com.google.android.material.imageview.ShapeableImageView


class WorkoutCategoriesAdapter(
    val context: Context,
    val list: ArrayList<JobsDataModel>,
) :
    RecyclerView.Adapter<WorkoutCategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_workout_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: JobsDataModel = list[position]
        holder.imgPic.setImageResource(model.image)
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context,FullImageWorkoutActivity::class.java))
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imgPic: ShapeableImageView = itemView.findViewById(R.id.imgPic)

    }




}