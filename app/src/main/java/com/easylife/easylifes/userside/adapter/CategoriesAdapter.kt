package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.instructor.InstructorActivity
import com.easylife.easylifes.model.home.CategoriesDataModel


class CategoriesAdapter(
    val context: Context,
    val list: ArrayList<CategoriesDataModel>,
) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.single_item_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: CategoriesDataModel = list[position]
        Glide.with(context).load(model.category_image).into(holder.imageHome)
        holder.trainerCategoryName.text = model.category_name
        holder.itemView.setOnClickListener {
            val intent = Intent(context,InstructorActivity::class.java)
            intent.putExtra("id",model.id.toString())
            context.startActivity(intent)
        }



    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imageHome: ImageView = itemView.findViewById(R.id.image_home)
        val trainerCategoryName: TextView = itemView.findViewById(R.id.text_trainBody)

    }




}