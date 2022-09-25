package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.instructor.InstructorDetailActivity
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.mealplan.MealTimeDataModel
import com.easylife.easylifes.trainerside.adapter.AllNutritionsAdapter

class ClientNutritionAdapter(
    val context: Context,
    val list: ArrayList<MealTimeDataModel>,
    var mListener: onFoodClick
) :
    RecyclerView.Adapter<ClientNutritionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_mealslist, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: MealTimeDataModel = list.get(position)
        holder.tvWeight.text = model.meal_time
        var calories = 0
        val listMeal = model.foods
        for (i in 0 until listMeal.size)
        {
            calories = (listMeal[i].food_details.meal_calories.toInt() * listMeal[i].food_details.serving_quantity.toInt()) + calories
        }
        holder.tvDescription.text = "("+calories.toString()+" calories)"
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
            mListener.onFoodClick(position)

        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onFoodClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvWeight: TextView = itemView.findViewById(R.id.tvName);
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription);
        val image_home: ImageView = itemView.findViewById(R.id.imgProfile)
        val rl: RelativeLayout = itemView.findViewById(R.id.rl)
        val imgTick: ImageView = itemView.findViewById(R.id.img)

    }
    interface onFoodClick {
        fun onFoodClick(position: Int)
    }



}