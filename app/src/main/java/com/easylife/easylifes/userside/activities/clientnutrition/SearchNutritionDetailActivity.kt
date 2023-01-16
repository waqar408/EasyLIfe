package com.easylife.easylifes.userside.activities.clientnutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivitySearchNutritionDetailBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.search.SearchDataModel
import com.easylife.easylifes.trainerside.activities.nutrition.ClientNutritionActivity
import com.easylife.easylifes.trainerside.activities.nutrition.SearchMealActivity
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Response

class SearchNutritionDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchNutritionDetailBinding
    private lateinit var utilities: Utilities
    private lateinit var searchDataModel: SearchDataModel
    var servingSize = ""
    var servingId = ""
    private var noOfServings = ""
    var planid = ""
    private var mealtimeid = ""
    private var mealid = ""
    private var nutritionName = ""
    var clientid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchNutritionDetailBinding.inflate(layoutInflater)
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
        utilities = Utilities(this@SearchNutritionDetailActivity)
        utilities.setGrayBar(this@SearchNutritionDetailActivity)
        val intent = intent
        planid = intent.getStringExtra("mealplanid").toString()
        mealtimeid = intent.getStringExtra("mealtimeid").toString()
        clientid = intent.getStringExtra("clientid").toString()
        nutritionName = intent.getStringExtra("nutritionName").toString()
        binding.tvNames.text = nutritionName
        val gson = Gson()
        searchDataModel =
            gson.fromJson(intent.getStringExtra("selectedMeal"), SearchDataModel::class.java)
        mealid = searchDataModel.id.toString()
        binding.tvName.text = searchDataModel.meal_title
        binding.tvCalories.text = searchDataModel.meal_calories
        binding.tvProtein.text = searchDataModel.meal_protien
        binding.tvCarbs.text = searchDataModel.meal_carbs
        binding.tvFat.text = searchDataModel.meal_fat
        binding.tvFiber.text = searchDataModel.meal_fibre
        binding.tvSodium.text = searchDataModel.meal_sodium
        binding.tvSugar.text = searchDataModel.meal_sugar
        val item = java.util.ArrayList<String>()
        for (i in 0 until searchDataModel.servings.size) {
            item.add(searchDataModel.servings[i].serving_size)
        }
        val adapter = ArrayAdapter(this@SearchNutritionDetailActivity, R.layout.spinner_text, item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                servingSize = searchDataModel.servings[position].serving_size
                servingId = searchDataModel.servings[position].id.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


}