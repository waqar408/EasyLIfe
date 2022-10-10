package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.trainerdetail.VideoListDataModel
import com.easylife.easylifes.trainerside.activities.FullScreenVideoActivity
import com.easylife.easylifes.userside.activities.inbox.InboxActivity
import com.google.android.material.imageview.ShapeableImageView


class TrainerVideosAdapter(
    val context: Context,
    val list: ArrayList<VideoListDataModel>,
) :
    RecyclerView.Adapter<TrainerVideosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_videos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: VideoListDataModel = list.get(position)
        Glide.with(context).load(model.video).into(holder.image_home)
        holder.rlDelete.visibility = View.GONE
        holder.itemView.setOnClickListener{
            val intent = Intent(context, FullScreenVideoActivity::class.java)
            intent.putExtra("videourl", model.video)
            context.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val image_home: ShapeableImageView = itemView.findViewById(R.id.imgPic);
        val rlDelete: RelativeLayout = itemView.findViewById(R.id.rlDelete);

    }




}