package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.mealplan.MealTimeDataModel
import com.google.android.material.imageview.ShapeableImageView


class MealTimesAdapter(
    val context: Context,
    val list: ArrayList<MealTimeDataModel>,
    var mListener: onMealTimeClick

) :
    RecyclerView.Adapter<MealTimesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_mealtime, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: MealTimeDataModel = list.get(position)
        holder.tvName.text = model.meal_time
        holder.itemView.setOnClickListener {

            mListener.onMealTimeClick(position)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onMealTimeClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val imgProfile: ShapeableImageView = itemView.findViewById(R.id.imgProfile)

    }

    interface onMealTimeClick {
        fun onMealTimeClick(position: Int)
    }




}