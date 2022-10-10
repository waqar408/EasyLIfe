package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.subscribedtrainer.SubscribedTrainerDataModel
import com.easylife.easylifes.trainerside.activities.nutrition.AllNutritionsActivity
import com.easylife.easylifes.userside.activities.clientnutrition.ClientNutritionsActivity
import com.google.android.material.imageview.ShapeableImageView

class SubscribedTrainerAdapter(
    val context: Context,
    val list: ArrayList<SubscribedTrainerDataModel>,
    var mListener: onAllClientDetailClick

) :
    RecyclerView.Adapter<SubscribedTrainerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_all_clients, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: SubscribedTrainerDataModel = list.get(position)
        holder.tvName.text = model.name
        val drawable = CircularProgressDrawable(context)
        drawable.setColorSchemeColors(R.color.appColor, R.color.appColor, R.color.appColor)
        drawable.setCenterRadius(25f)
        drawable.setStrokeWidth(6f)
        drawable.start()
        Glide.with(context).load(model.profile_image).placeholder(drawable).into(holder.imgProfile)
        holder.tvUserName.text = model.username

        if (model.workout_assigned == true)
        {
            holder.rlWorkout.visibility = View.VISIBLE
        }else{
            holder.rlWorkout.visibility = View.GONE
        }
        if (model.meal_plan_assigned == true)
        {
            holder.tvNutrition.visibility = View.VISIBLE
        }else{
            holder.tvNutrition.visibility = View.GONE
        }
        holder.tvNutrition.setOnClickListener {
            val intent = Intent(context, ClientNutritionsActivity::class.java)
            intent.putExtra("clientid",model.id.toString())
            context.startActivity(intent)
        }

        holder.rlWorkout.setOnClickListener {
            mListener.onClickArea(position)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onAllClientDetailClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName);
        val tvUserName: TextView = itemView.findViewById(R.id.tvusername);
        val imgProfile: ShapeableImageView = itemView.findViewById(R.id.imgProfile);
        val rlWorkout: RelativeLayout = itemView.findViewById(R.id.rlWorkout);
        val tvNutrition: TextView = itemView.findViewById(R.id.rlNutrition);

    }

    interface onAllClientDetailClick {
        fun onClickArea(position: Int)
    }




}