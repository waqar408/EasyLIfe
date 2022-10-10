package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.model.getuserworkouts.UserWorkoutRepsDataModel
import com.easylife.easylifes.model.mealtimes.MealTimeFoodsDataModel


class SubNutritionDetailAdapter(val context: Context, val list: ArrayList<MealTimeFoodsDataModel>?) : RecyclerView.Adapter<SubNutritionDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_nutrition_meal_name, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: MealTimeFoodsDataModel = list!!.get(position)
        holder.tvCal.text = model.food_details.meal_calories+" Kcal"
        holder.tvServings.text = model.food_details.serving_quantity
        holder.tvMealTimeName.text = model.food_details.meal_title

    }



    override fun getItemCount(): Int {
        return list!!.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvMealTimeName: TextView = itemView.findViewById(R.id.tvMealTimeName)
        val tvServings: TextView = itemView.findViewById(R.id.tvServings)
        val tvCal: TextView = itemView.findViewById(R.id.tvCal)

    }
}