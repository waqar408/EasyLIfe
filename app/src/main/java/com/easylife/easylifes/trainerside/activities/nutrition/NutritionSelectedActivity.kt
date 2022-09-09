package com.easylife.easylifes.trainerside.activities.nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityNutritionSelectedBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.trainerside.adapter.SelectedNutritionAdapter
import com.easylife.easylifes.utils.Utilities

class NutritionSelectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNutritionSelectedBinding
    private lateinit var utilities: Utilities
    private lateinit var listSelectionNutrition: ArrayList<JobsDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionSelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()
    }
    private fun onClicks() {
        binding.layoutCreateWorkout.setOnClickListener {
            startActivity(
                Intent(
                    this@NutritionSelectedActivity,
                    CreateNutrtionActivity::class.java
                )
            )
        }
    }

    private fun initViews() {
        utilities = Utilities(this@NutritionSelectedActivity)
        utilities.setGrayBar(this@NutritionSelectedActivity)

        listSelectionNutrition = ArrayList()
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon, "Egg Yolk"))
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon, "Egg Yolk"))
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon, "Egg Yolk"))
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon, "Egg Yolk"))
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon, "Egg Yolk"))
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon, "Egg Yolk"))

        binding.rvSelectedNutrition.layoutManager = LinearLayoutManager(
            this@NutritionSelectedActivity,
            LinearLayoutManager.VERTICAL, false
        )
        binding.rvSelectedNutrition.adapter =
            SelectedNutritionAdapter(this@NutritionSelectedActivity, listSelectionNutrition)
    }
}