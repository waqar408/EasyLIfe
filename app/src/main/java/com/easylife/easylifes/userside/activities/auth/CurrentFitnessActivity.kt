package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityCurrentFitnessBinding
import com.easylife.easylifes.utils.Utilities

class CurrentFitnessActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCurrentFitnessBinding
    private lateinit var utilities: Utilities
    var gender = ""
    var age = ""
    var weight = ""
    private var weightUnit = ""
    var height = ""
    private var heightUnit = ""
    private var interest = ""
    private var achieve = ""
    private var currentFitnessLevel = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        utilities = Utilities(this@CurrentFitnessActivity)
        utilities.setWhiteBars(this@CurrentFitnessActivity)
        gender = intent.getStringExtra("gender").toString()
        age = intent.getStringExtra("age").toString()
        weight = intent.getStringExtra("weight").toString()
        weightUnit = intent.getStringExtra("wUnit").toString()
        heightUnit = intent.getStringExtra("hunit").toString()
        height = intent.getStringExtra("height").toString()
        interest = intent.getStringExtra("interest").toString()
        achieve = intent.getStringExtra("goal").toString()
        binding.getfilter.setOnClickListener {
            binding.getfilter.setBackgroundResource(R.drawable.btn_outline_back)
            binding.getfilter.setTextColor(resources.getColor(R.color.appColor))
            binding.sleeping.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.sleeping.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.weight.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.weight.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.track.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.track.setTextColor(resources.getColor(R.color.smokey_grey))
            currentFitnessLevel= "Beginner"
        }
        binding.sleeping.setOnClickListener {
            binding.getfilter.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.getfilter.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.sleeping.setBackgroundResource(R.drawable.btn_outline_back)
            binding.sleeping.setTextColor(resources.getColor(R.color.appColor))
            binding.weight.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.weight.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.track.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.track.setTextColor(resources.getColor(R.color.smokey_grey))

            currentFitnessLevel= "Intermediate"

        }
        binding.weight.setOnClickListener {
            binding.getfilter.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.getfilter.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.sleeping.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.sleeping.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.weight.setBackgroundResource(R.drawable.btn_outline_back)
            binding.weight.setTextColor(resources.getColor(R.color.appColor))
            binding.track.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.track.setTextColor(resources.getColor(R.color.smokey_grey))

            currentFitnessLevel= "Advance"

        }
        binding.track.setOnClickListener {
            binding.getfilter.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.getfilter.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.sleeping.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.sleeping.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.weight.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.weight.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.track.setBackgroundResource(R.drawable.btn_outline_back)
            binding.track.setTextColor(resources.getColor(R.color.appColor))
            currentFitnessLevel= "I always workout regular 2 or 3 times a week."

        }
        binding.layoutSend.setOnClickListener {
            if (currentFitnessLevel == "")
            {
                utilities.showFailureToast(this@CurrentFitnessActivity,"Please Select Your Current Fitness Level")
            }else{
                val intent = Intent(this@CurrentFitnessActivity,CustomizeInterestActivity::class.java)
                intent.putExtra("gender",gender)
                intent.putExtra("age",age)
                intent.putExtra("wUnit",weightUnit)
                intent.putExtra("weight",weight)
                intent.putExtra("height",height)
                intent.putExtra("hunit",heightUnit)
                intent.putExtra("interest",interest)
                intent.putExtra("goal",achieve)
                intent.putExtra("currentfitness",currentFitnessLevel)
                startActivity(intent)
            }
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }
}