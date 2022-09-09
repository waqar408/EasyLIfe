package com.easylife.easylifes.trainerside.activities.nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityCreateNutrtionBinding
import com.easylife.easylifes.utils.Utilities

class CreateNutrtionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreateNutrtionBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNutrtionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }
    private fun onClicks() {
        binding.layoutCreateWorkout.setOnClickListener {
            startActivity(Intent(this@CreateNutrtionActivity,ClientNutritionActivity::class.java))
        }    }

    private fun initViews() {
        utilities = Utilities(this@CreateNutrtionActivity)
        utilities.setGrayBar(this@CreateNutrtionActivity)    }
}
