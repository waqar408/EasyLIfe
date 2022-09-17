package com.easylife.easylifes.trainerside.activities.nutrition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityMacronutrientsDetailBinding
import com.easylife.easylifes.model.mealplan.MealPlansDataModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson

class MacronutrientsDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMacronutrientsDetailBinding
    private lateinit var modelPlan: MealPlansDataModel
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMacronutrientsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        utilities = Utilities(this@MacronutrientsDetailActivity)
        utilities.setWhiteBars(this@MacronutrientsDetailActivity)
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        val intent = intent
        val gson = Gson()
        modelPlan = gson.fromJson(intent.getStringExtra("myplan"), MealPlansDataModel::class.java)
        binding.edCalories.text= modelPlan.calorie_target
        binding.edProtein.text =modelPlan.protien
        binding.edCarbs.text = modelPlan.carbs
        binding.edFat.text =modelPlan.fat
        binding.edFiber.text = modelPlan.fibre
        binding.edSodium.text =modelPlan.sodium
        binding.edSugar.text  = modelPlan.sugar
    }
}