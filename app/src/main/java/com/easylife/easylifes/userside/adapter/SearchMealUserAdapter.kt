package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.model.search.SearchDataModel
import com.google.android.material.imageview.ShapeableImageView


class SearchMealUserAdapter(
    val context: Context,
    val list: ArrayList<SearchDataModel>,
    var mListener: onMealTimeClick

) :
    RecyclerView.Adapter<SearchMealUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_mealslist, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: SearchDataModel = list[position]

        holder.rl.visibility = View.GONE
        holder.tvName.text = model.meal_title
        holder.tvDescription.text ="("+ model.meal_calories+")"+" calories"
        holder.itemView.setOnClickListener {

            mListener.onMealTimeClick(position)
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onMealTimeClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val rl: RelativeLayout = itemView.findViewById(R.id.rl)
        val imgTick: ImageView = itemView.findViewById(R.id.img)
        val imgProfile: ShapeableImageView = itemView.findViewById(R.id.imgProfile)

    }

    interface onMealTimeClick {
        fun onMealTimeClick(position: Int)
    }




}