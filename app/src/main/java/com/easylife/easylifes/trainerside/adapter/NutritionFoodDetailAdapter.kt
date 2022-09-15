package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.mealplan.FoodDataModel
import com.easylife.easylifes.model.mealplan.FoodDetailDataModel
import com.easylife.easylifes.model.trainerhome.TrainerUserDataModel
import com.easylife.easylifes.trainerside.activities.nutrition.AllNutritionsActivity
import com.google.android.material.imageview.ShapeableImageView


class NutritionFoodDetailAdapter(
    val context: Context,
    val list: ArrayList<FoodDataModel>,
    var mListener: onAllClientDetailClick

) :
    RecyclerView.Adapter<NutritionFoodDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_fooddetail, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: FoodDataModel = list.get(position)
        holder.tvCalories.text = model.food_details.meal_calories
        holder.tvProtein.text = model.food_details.meal_protien
        holder.tvCarbs.text = model.food_details.meal_carbs
        holder.tvFat.text = model.food_details.meal_fat
        holder.tvFiber.text = model.food_details.meal_fibre
        holder.tvSodium.text = model.food_details.meal_sodium
        holder.tvSugar.text = model.food_details.meal_sugar
        holder.tvServingSize.text = model.food_details.serving_size
        holder.edNoOfServing.text = model.food_details.serving_quantity
        holder.tvName.text = model.food_details.meal_title
        holder.itemView.setOnClickListener {
            mListener.onClickArea(position)
        }


    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onAllClientDetailClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvCalories: TextView = itemView.findViewById(R.id.tvCalories);
        val tvProtein: TextView = itemView.findViewById(R.id.tvProtein);
        val tvCarbs: TextView = itemView.findViewById(R.id.tvCarbs);
        val tvFat: TextView = itemView.findViewById(R.id.tvFat);
        val tvFiber: TextView = itemView.findViewById(R.id.tvFiber);
        val tvSodium: TextView = itemView.findViewById(R.id.tvSodium);
        val tvSugar: TextView = itemView.findViewById(R.id.tvSugar);
        val tvServingSize: TextView = itemView.findViewById(R.id.spinner);
        val edNoOfServing: TextView = itemView.findViewById(R.id.edNoOfServing);
        val tvName: TextView = itemView.findViewById(R.id.tvName);

    }

    interface onAllClientDetailClick {
        fun onClickArea(position: Int)
    }




}