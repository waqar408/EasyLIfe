package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.trainerside.activities.FullScreenVideoActivity


class SelectedWorkoutAdapter(
    val context: Context,
    val list: ArrayList<AllWorkoutsDataListModel>,
    var mListener: onCategoryVideoClick
) :
    RecyclerView.Adapter<SelectedWorkoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_selected_workout, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: AllWorkoutsDataListModel = list[position]
        holder.tvName.text = model.title
        holder.tvDescription.text = model.description
        Glide.with(context).load(model.media).into(holder.imgProfile)
        holder.rlDelete.setOnClickListener {
            mListener.onClickArea(position,holder)
        }
        holder.lnVideo.setOnClickListener {
            val intent = Intent(context, FullScreenVideoActivity::class.java)
            intent.putExtra("videourl", model.media)
            context.startActivity(intent)
        }
        holder.imgProfile.setOnClickListener {
            val intent = Intent(context, FullScreenVideoActivity::class.java)
            intent.putExtra("videourl", model.media)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View,listener: onCategoryVideoClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val imgProfile: ImageView = itemView.findViewById(R.id.imgProfile)
        val rlDelete: RelativeLayout = itemView.findViewById(R.id.rlDelete)
        val lnVideo: LinearLayout = itemView.findViewById(R.id.lnVideo)


    }

    interface onCategoryVideoClick {
        fun onClickArea(position: Int,holder : ViewHolder)
    }


}