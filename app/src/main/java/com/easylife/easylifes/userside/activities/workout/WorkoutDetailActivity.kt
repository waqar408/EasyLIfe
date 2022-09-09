package com.easylife.easylifes.userside.activities.workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.userside.activities.clientnutrition.ClientNutritionsActivity
import com.easylife.easylifes.databinding.ActivityWorkoutDetailBinding
import com.easylife.easylifes.utils.Utilities

class WorkoutDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWorkoutDetailBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()


    }

    private fun onClicks() {
        binding.lnExcercise.setOnClickListener {
            startActivity(Intent(this@WorkoutDetailActivity,ClientNutritionsActivity::class.java))
        }

        binding.rlWorkoutCategories.setOnClickListener {
            startActivity(Intent(this@WorkoutDetailActivity,WorkoutCategoryActivity::class.java))
        }    }

    private fun initViews() {
        utilities = Utilities(this@WorkoutDetailActivity)
        utilities.setGreenBar(this@WorkoutDetailActivity)    }
}