package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.trainerside.activities.nutrition.NutritionSelectedActivity


class SelectionNutritionAdapter(
    val context: Context,
    val list: ArrayList<JobsDataModel>,
) :
    RecyclerView.Adapter<SelectionNutritionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_selection_nutrition, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: JobsDataModel = list.get(position)
        holder.tvName.text = model.name
        holder.imgProfile.setImageResource(model.image)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, NutritionSelectedActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName);
        val imgProfile: ImageView = itemView.findViewById(R.id.imgProfile);

    }




}