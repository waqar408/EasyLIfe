package com.easylife.easylifes.trainerside.adapter

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
import com.easylife.easylifes.model.trainerhome.TrainerUserDataModel
import com.easylife.easylifes.trainerside.activities.clientdetail.AllWorkoutsActivity
import com.easylife.easylifes.trainerside.activities.nutrition.AllNutritionsActivity
import com.easylife.easylifes.trainerside.activities.nutrition.MealTimesActivity
import com.google.android.material.imageview.ShapeableImageView
import org.w3c.dom.Text


class AllClientsListAdapter(
    val context: Context,
    val list: ArrayList<TrainerUserDataModel>,
    var mListener: onAllClientDetailClick

) :
    RecyclerView.Adapter<AllClientsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_all_clients, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: TrainerUserDataModel = list.get(position)
        holder.tvName.text = model.name
        Glide.with(context).load(model.profile_image).into(holder.imgProfile)
        holder.tvUserName.text = model.username
        holder.rlWorkout.setOnClickListener {
            mListener.onClickArea(position)
        }
        holder.tvNutrition.setOnClickListener {
            val intent = Intent(context, AllNutritionsActivity::class.java)
            intent.putExtra("clientid",model.id.toString())
            context.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View,listener: onAllClientDetailClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName);
        val tvUserName: TextView = itemView.findViewById(R.id.tvUserName);
        val imgProfile: ShapeableImageView = itemView.findViewById(R.id.imgProfile);
        val rlWorkout: RelativeLayout = itemView.findViewById(R.id.rlWorkout);
        val tvNutrition: TextView = itemView.findViewById(R.id.rlNutrition);

    }

    interface onAllClientDetailClick {
        fun onClickArea(position: Int)
    }




}