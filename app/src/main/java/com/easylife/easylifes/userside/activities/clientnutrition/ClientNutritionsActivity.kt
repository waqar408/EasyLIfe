package com.easylife.easylifes.userside.activities.clientnutrition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.ClientNutritionAdapter
import com.easylife.easylifes.databinding.ActivityClientNutritionsBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.utils.Utilities

class ClientNutritionsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityClientNutritionsBinding
    private lateinit var utilities: Utilities
    private lateinit var clientNutritionList : ArrayList<JobsDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientNutritionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()



    }

    private fun initViews() {
        utilities = Utilities(this@ClientNutritionsActivity)
        utilities.setGrayBar(this@ClientNutritionsActivity)
        clientNutritionList= ArrayList()
        clientNutritionList.add(JobsDataModel(R.drawable.nutritionimg,"A.M.Snack"))
        clientNutritionList.add(JobsDataModel(R.drawable.nutritionimg,"A.M.Snack"))
        clientNutritionList.add(JobsDataModel(R.drawable.nutritionimg,"A.M.Snack"))
        clientNutritionList.add(JobsDataModel(R.drawable.nutritionimg,"A.M.Snack"))

        binding.rvClientNutrition.layoutManager = LinearLayoutManager(this@ClientNutritionsActivity,LinearLayoutManager.VERTICAL,false)
        binding.rvClientNutrition.adapter  = ClientNutritionAdapter(this@ClientNutritionsActivity,clientNutritionList)
    }
}