package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.follower.FollowerFollowingActivity
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.subscribedtrainer.SubscribedTrainerDataModel
import com.easylife.easylifes.model.subscribedtrainer.SubscribedTrainerResponseModel
import com.easylife.easylifes.userside.activities.instructor.InstructorActivity
import com.easylife.easylifes.userside.activities.instructor.InstructorDetailActivity


class TopTrainerAdapter(
    val context: Context,
    val list: ArrayList<SubscribedTrainerDataModel>,
) :
    RecyclerView.Adapter<TopTrainerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_top_instructor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: SubscribedTrainerDataModel = list.get(position)
        val drawable = CircularProgressDrawable(context)
        drawable.setColorSchemeColors(R.color.appColor, R.color.appColor, R.color.appColor)
        drawable.setCenterRadius(25f)
        drawable.setStrokeWidth(6f)
        drawable.start()
        Glide.with(context).load(model.profile_image).placeholder(drawable).into(holder.image_home)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, InstructorDetailActivity::class.java)
            intent.putExtra("id",model.id.toString())
            context.startActivity(intent)
        }

        holder.trainerName.text = model.name


    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val image_home: ImageView = itemView.findViewById(R.id.imgTrainer);
        val trainerName: TextView = itemView.findViewById(R.id.trainerName);

    }




}