package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.review.ReviewListActivity
import com.easylife.easylifes.model.JobsDataModel
import com.google.android.material.imageview.ShapeableImageView


class FollowerAdapter(
    val context: Context,
    val list: ArrayList<JobsDataModel>,
) :
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_followers, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: JobsDataModel = list.get(position)
        holder.tvWeight.text = model.name
        holder.image_home.setImageResource(model.image)
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, ReviewListActivity::class.java))
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvWeight: TextView = itemView.findViewById(R.id.tvName);
        val image_home: ShapeableImageView = itemView.findViewById(R.id.imgProfile);

    }




}