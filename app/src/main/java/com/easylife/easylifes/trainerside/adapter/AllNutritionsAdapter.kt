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


class AllNutritionsAdapter(
    val context: Context,
    val list: ArrayList<JobsDataModel>,
    var mListener: onAllClientDetailClick

) :
    RecyclerView.Adapter<AllNutritionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_all_workout, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: JobsDataModel = list.get(position)
        holder.tvName.text = model.name
        holder.imgProfile.setImageResource(model.image)
        holder.itemView.setOnClickListener {
            mListener.onClickArea(position)
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onAllClientDetailClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvPlans);
        val imgProfile: ImageView = itemView.findViewById(R.id.imgPic);

    }

    interface onAllClientDetailClick {
        fun onClickArea(position: Int)
    }




}