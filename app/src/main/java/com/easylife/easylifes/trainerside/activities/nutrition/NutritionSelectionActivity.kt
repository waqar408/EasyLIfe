package com.easylife.easylifes.trainerside.activities.nutrition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityNutritionSelectionBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.trainerside.adapter.SelectionNutritionAdapter
import com.easylife.easylifes.utils.Utilities

class NutritionSelectionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNutritionSelectionBinding
    private lateinit var utilities: Utilities
    private lateinit var listSelectionNutrition : ArrayList<JobsDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()


    }
    private fun initViews() {
        utilities  = Utilities(this@NutritionSelectionActivity)
        utilities.setGrayBar(this@NutritionSelectionActivity)

        listSelectionNutrition = ArrayList()
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon,"Egg Yolk"))
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon,"Egg Yolk"))
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon,"Egg Yolk"))
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon,"Egg Yolk"))
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon,"Egg Yolk"))
        listSelectionNutrition.add(JobsDataModel(R.drawable.personprofileicon,"Egg Yolk"))

        binding.rvNutrition.layoutManager = LinearLayoutManager(this@NutritionSelectionActivity,
            LinearLayoutManager.VERTICAL,false)
        binding.rvNutrition.adapter = SelectionNutritionAdapter(this@NutritionSelectionActivity,listSelectionNutrition)
    }
}