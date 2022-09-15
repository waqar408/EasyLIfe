package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.model.mealplan.MealTimeDataModel
import com.easylife.easylifes.model.search.SearchDataModel
import com.google.android.material.imageview.ShapeableImageView


class SearchMealAdapter(
    val context: Context,
    val list: ArrayList<SearchDataModel>,
    var mListener: onMealTimeClick

) :
    RecyclerView.Adapter<SearchMealAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_mealslist, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: SearchDataModel = list.get(position)

        holder.tvName.text = model.meal_title
        holder.tvDescription.text ="("+ model.meal_calories+")"+" calories"
        holder.itemView.setOnClickListener {
            model.isChecked = !model.isChecked
            if (model.isChecked) {
                holder.rl.setBackgroundResource(R.drawable.selected_greenback)
                holder.imgTick.setColorFilter(ContextCompat.getColor(context, R.color.white))
            }
            if (!model.isChecked) {
                holder.rl.setBackgroundResource(R.drawable.btn_outline_light_color)
                holder.imgTick.setColorFilter(ContextCompat.getColor(context, R.color.white))
            }
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