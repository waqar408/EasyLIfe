package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.instructor.InstructorDetailActivity
import com.easylife.easylifes.model.categorytrainer.CategoryTrainerDataModel
import com.google.android.material.imageview.ShapeableImageView


class InstructorAdapter(
    val context: Context,
    val list: ArrayList<CategoryTrainerDataModel>,
) :
    RecyclerView.Adapter<InstructorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_trainer_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: CategoryTrainerDataModel = list[position]
        holder.tvName.text = model.name
        holder.specialization.text = model.specialization
        holder.experience.text = model.experience
        holder.rating.text = model.average_rating
        Glide.with(context).load(model.profile_image).into(holder.imageHome)
        holder.itemView.setOnClickListener {
            val intent= Intent(Intent(context,InstructorDetailActivity::class.java))
            intent.putExtra("id",model.id.toString())
            context.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val specialization: TextView = itemView.findViewById(R.id.specialization)
        val experience: TextView = itemView.findViewById(R.id.experience)
        val rating: TextView = itemView.findViewById(R.id.rating)
        val imageHome: ShapeableImageView = itemView.findViewById(R.id.imgProfile)

    }




}