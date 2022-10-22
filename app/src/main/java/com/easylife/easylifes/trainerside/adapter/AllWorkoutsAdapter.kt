package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutDataListModel


class AllWorkoutsAdapter(
    val context: Context,
    val list: ArrayList<GetUserWorkoutDataListModel>,
    var mListener: onAllWorkoutClick

) :
    RecyclerView.Adapter<AllWorkoutsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_all_workout, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: GetUserWorkoutDataListModel = list.get(position)
        holder.tvWorkoutCategoryName.text = model.title
        Glide.with(context).load(R.drawable.gradiant).into(holder.imgWorkoutCategory)
        holder.itemView.setOnClickListener {
            mListener.onClickArea(position)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View,listener: onAllWorkoutClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvWorkoutCategoryName: TextView = itemView.findViewById(R.id.tvPlans)
        val imgWorkoutCategory: ImageView = itemView.findViewById(R.id.imgPic)

    }

    interface onAllWorkoutClick {
        fun onClickArea(position: Int)
    }




}