package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.getuserworkouts.UserWorkoutRepsDataModel
import com.easylife.easylifes.model.getuserworkouts.UserWorkoutVideoListModel
import com.easylife.easylifes.trainerside.activities.FullScreenVideoActivity
import com.easylife.easylifes.trainerside.adapter.WorkoutDetailRepsAndRestAdapter
import com.google.android.material.imageview.ShapeableImageView


class WorkoutDetailAdapter(
    val context: Context,
    val list: ArrayList<UserWorkoutVideoListModel>
) :
    RecyclerView.Adapter<WorkoutDetailAdapter.ViewHolder>() {
    private var viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_workout_videodetail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: UserWorkoutVideoListModel = list[position]
        holder.tvName.text = model.title
        Glide.with(context).load(model.media).into(holder.imgProfile)
        holder.tvDescription.text = model.description
        val list: ArrayList<UserWorkoutRepsDataModel> = model.data
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.rvRepsAndRest.layoutManager = layoutManager
        val adapter = WorkoutDetailRepsAndRestAdapter(context, list)
        holder.rvRepsAndRest.adapter = adapter
        holder.rvRepsAndRest.setRecycledViewPool(viewPool)


        holder.layoutComplete.visibility = View.GONE
        holder.layoutDelete.visibility = View.GONE

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

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val imgProfile: ShapeableImageView = itemView.findViewById(R.id.imgProfile)
        val rvRepsAndRest: RecyclerView = itemView.findViewById(R.id.rvRepsAndRest)
        val layoutComplete: RelativeLayout = itemView.findViewById(R.id.layoutComplete)
        val layoutDelete: RelativeLayout = itemView.findViewById(R.id.layoutDelete)
        val lnVideo: LinearLayout = itemView.findViewById(R.id.lnVideo)

    }



}