package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.model.mealtimes.MealTimeFoodsDataModel
import com.easylife.easylifes.model.mealtimes.MealTimesDataModel
import com.easylife.easylifes.trainerside.adapter.SubNutritionDetailAdapter

class NutritionDetailAdapter(
    val context: Context,
    val list: ArrayList<MealTimesDataModel>

    ) :
    RecyclerView.Adapter<NutritionDetailAdapter.ViewHolder>() {
    private var viewPool = RecyclerView.RecycledViewPool()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_nutrition_detail, parent, false)
        return ViewHolder(view)
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

        holder.layoutComplete.visibility = View.GONE
        holder.layoutDelete.visibility = View.GONE

    }





    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName);
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription);
        val tvTotalCalories: TextView = itemView.findViewById(R.id.tvTotalCalories);
        val rvRepsAndRest: RecyclerView = itemView.findViewById(R.id.rvRepsAndRest)
        val layoutComplete: RelativeLayout = itemView.findViewById(R.id.layoutComplete)
        val lnVideo: LinearLayout = itemView.findViewById(R.id.lnVideo)
        val layoutDelete: RelativeLayout = itemView.findViewById(R.id.layoutDelete)

    }

}