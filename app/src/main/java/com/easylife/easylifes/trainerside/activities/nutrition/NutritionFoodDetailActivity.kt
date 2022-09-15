package com.easylife.easylifes.trainerside.activities.nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityClientNutritionBinding
import com.easylife.easylifes.databinding.ActivityNutritionFoodDetailBinding
import com.easylife.easylifes.model.mealplan.FoodDataModel
import com.easylife.easylifes.model.mealplan.MealTimeDataModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.trainerside.adapter.NutritionFoodDetailAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson

class NutritionFoodDetailActivity : AppCompatActivity(),
    NutritionFoodDetailAdapter.onAllClientDetailClick {
    private lateinit var binding: ActivityNutritionFoodDetailBinding
    private lateinit var utilities: Utilities
    lateinit var mealtimemodel: MealTimeDataModel
    var clientid = ""
    var mealPlanId = ""
    var mealtimeid = ""
    var from = ""
    var nutritionName = ""
    private lateinit var list: ArrayList<FoodDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initViews()
        onClicks()


    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.rlAddNutrition.setOnClickListener {
            val intent = Intent(this@NutritionFoodDetailActivity, SearchMealActivity::class.java)
            intent.putExtra("clientid", clientid)
            intent.putExtra("planid", mealPlanId)
            intent.putExtra("mealtimeid", mealtimeid)
            intent.putExtra("from", from)
            intent.putExtra("nutritionName", nutritionName)
            startActivity(intent)
            finish()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@NutritionFoodDetailActivity)
        utilities.setWhiteBars(this@NutritionFoodDetailActivity)
        list = ArrayList()
        val intent = intent
        val gsonn = Gson()
        clientid = intent.getStringExtra("clientid").toString()
        mealPlanId = intent.getStringExtra("mealId").toString()
        mealtimeid = intent.getStringExtra("mealtimeid").toString()
        nutritionName = intent.getStringExtra("nutritionName").toString()
        val jsonn: String = intent.getStringExtra("foodlist").toString()
        if (!jsonn.isEmpty()) {

            val mealtimemodel = gsonn.fromJson(jsonn, MealTimeDataModel::class.java)
            list = mealtimemodel.foods
        }

        binding.rvNutritionFood.layoutManager =
            LinearLayoutManager(this@NutritionFoodDetailActivity)
        binding.rvNutritionFood.adapter =
            NutritionFoodDetailAdapter(this@NutritionFoodDetailActivity, list, this)
    }

    override fun onClickArea(position: Int) {

        //nothing for now
    }
}