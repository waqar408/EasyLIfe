package com.easylife.easylifes.trainerside.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.model.allworkouts.WorkoutRepsAndRestModel
import com.easylife.easylifes.model.mealtimes.MealTimeFoodsDataModel
import com.easylife.easylifes.model.mealtimes.MealTimesDataModel
import com.easylife.easylifes.trainerside.activities.nutrition.SearchMealActivity


class UserNutritionDetailAdapter(
    val context: Context,
    val list: ArrayList<MealTimesDataModel>,
    var mListener: onSelectedWorkoutClick,
    var mListenerAdd : onAddMealClick,

) :
    RecyclerView.Adapter<UserNutritionDetailAdapter.ViewHolder>() {
    private var viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_nutrition_detail, parent, false)
        return ViewHolder(view, mListener,mListenerAdd)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: MealTimesDataModel = list.get(position)
        var totalCalories = ""
        holder.tvName.text = model.meal_time
        var list: ArrayList<MealTimeFoodsDataModel> = ArrayList()
        list = model.foods
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.rvRepsAndRest.layoutManager = layoutManager
        val adapter = SubNutritionDetailAdapter(context, list)
        for (i in 0 until  list.size)
        {
            totalCalories = list.get(position).food_details.meal_calories
        }
        if(totalCalories.equals(""))
        {
            holder.tvTotalCalories.text = totalCalories +"0 Kcal"

        }else{
            holder.tvTotalCalories.text = totalCalories +" Kcal"
        }
        holder.rvRepsAndRest.adapter = adapter
        holder.rvRepsAndRest.setRecycledViewPool(viewPool)


        holder.layoutDelete.setOnClickListener {
            mListener.onClickArea(position)
        }

        holder.layoutComplete.setOnClickListener {
            mListenerAdd.onAddMealClick(position)
        }



    }





    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onSelectedWorkoutClick,listner : onAddMealClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName);
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription);
        val tvTotalCalories: TextView = itemView.findViewById(R.id.tvTotalCalories);
        val rvRepsAndRest: RecyclerView = itemView.findViewById(R.id.rvRepsAndRest)
        val layoutComplete: RelativeLayout = itemView.findViewById(R.id.layoutComplete)
        val layoutDelete: RelativeLayout = itemView.findViewById(R.id.layoutDelete)
        val lnVideo: LinearLayout = itemView.findViewById(R.id.lnVideo)

    }

    interface onSelectedWorkoutClick {
        fun onClickArea(position: Int)
    }

    interface onAddMealClick {
        fun onAddMealClick(position: Int)
    }


}