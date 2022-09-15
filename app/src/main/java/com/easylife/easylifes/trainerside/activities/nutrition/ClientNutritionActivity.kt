package com.easylife.easylifes.trainerside.activities.nutrition

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.ActivityClientNutritionBinding
import com.easylife.easylifes.model.mealplan.FoodDataModel
import com.easylife.easylifes.model.mealplan.MealPlansDataModel
import com.easylife.easylifes.model.mealplan.MealTimeDataModel
import com.easylife.easylifes.userside.adapter.ClientNutritionAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson


class ClientNutritionActivity : AppCompatActivity(), ClientNutritionAdapter.onFoodClick {
    private lateinit var utilities: Utilities
    private lateinit var binding: ActivityClientNutritionBinding
    private lateinit var modelPlan: MealPlansDataModel
    private lateinit var mealTimesList: ArrayList<MealTimeDataModel>
    var clientid = ""
    var planid = ""
    var mealtimeid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientNutritionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@ClientNutritionActivity)
        utilities.setGrayBar(this@ClientNutritionActivity)
        val intent = intent
        val gson = Gson()
        modelPlan = gson.fromJson(intent.getStringExtra("myplan"), MealPlansDataModel::class.java)
        clientid = intent.getStringExtra("clientid").toString()
        planid = modelPlan.id.toString()

        mealTimesList = ArrayList()
        var mealTimesLists: ArrayList<FoodDataModel> = ArrayList()
        var calories = 0.0
        var protein = 0.0
        var carbs = 0.0
        var fiber = 0.0
        var fat = 0.0
        var sodium = 0.0
        mealTimesList = modelPlan.meal_times

        if (mealTimesList.size > 0) {
            for (i in 0 until mealTimesList.size) {

                mealTimesLists = mealTimesList.get(i).foods
                if (mealTimesLists.size > 0) {
                    for (j in 0 until mealTimesLists.size) {
                        calories = mealTimesLists[j].food_details.meal_calories.toDouble() + calories
                        protein = mealTimesLists[j].food_details.meal_protien.toDouble() + protein
                        carbs = mealTimesLists[j].food_details.meal_carbs.toDouble() + carbs
                        fiber = mealTimesLists[j].food_details.meal_fibre.toDouble() + fiber
                        fat = mealTimesLists[j].food_details.meal_fat.toDouble() + fat
                        sodium = mealTimesLists[j].food_details.meal_sodium.toDouble() + sodium
                    }
                }
            }
        }

        val caloriess = calories.toString()
        val proteins = protein.toString()
        val carbss = carbs.toString()
        val fibers = fiber.toString()
        val fatss = fat.toString()
        val sodiums = sodium.toString()
        val separated1 = caloriess.split(".").toTypedArray()
        val separated2 = proteins.split(".").toTypedArray()
        val separated3 = carbss.split(".").toTypedArray()
        val separated4 = fibers.split(".").toTypedArray()
        val separated5 = fatss.split(".").toTypedArray()
        val separated6 = sodiums.split(".").toTypedArray()


        binding.rvClientNutrition.layoutManager = LinearLayoutManager(this@ClientNutritionActivity)
        binding.rvClientNutrition.adapter = ClientNutritionAdapter(this@ClientNutritionActivity,mealTimesList, this@ClientNutritionActivity)
        binding.tvCalories.text = separated1[0]
        binding.tvProtein.text = separated2[0]
        binding.tvCarbs.text = separated3[0]
        binding.tvFiber.text = separated4[0]
        binding.tvfat.text = separated5[0]
        binding.tvSodium.text = separated6[0]

    }

    override fun onFoodClick(position: Int) {
        val model = mealTimesList[position]
        val gson = Gson()
        val mySelectMeal = gson.toJson(model)
        val intent = Intent(this@ClientNutritionActivity, NutritionFoodDetailActivity::class.java)
        intent.putExtra("foodlist", mySelectMeal)
        intent.putExtra("clientid",clientid)
        intent.putExtra("mealId",planid.toString())
        intent.putExtra("mealtimeid",model.id.toString())
        startActivity(intent)

    }
}