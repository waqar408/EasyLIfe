package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.follower.FollowerFollowingActivity
import com.easylife.easylifes.model.JobsDataModel


class TopTrainerAdapter(
    val context: Context,
    val list: ArrayList<JobsDataModel>,
) :
    RecyclerView.Adapter<TopTrainerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_top_instructor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: JobsDataModel = list.get(position)
        holder.image_home.setImageResource(model.image)
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context,FollowerFollowingActivity::class.java))
        }


    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val image_home: ImageView = itemView.findViewById(R.id.imgTrainer);

    }




}