package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityGoalBinding
import com.easylife.easylifes.utils.Utilities

class GoalActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGoalBinding
    private var interest = ""
    var gender = ""
    var age = ""
    var weight = ""
    private var weightUnit = ""
    var height = ""
    private var heightUnit = ""
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        utilities = Utilities(this@GoalActivity)
        utilities.setWhiteBars(this@GoalActivity)
        val intent = intent
        gender = intent.getStringExtra("gender").toString()
        age = intent.getStringExtra("age").toString()
        weight = intent.getStringExtra("weight").toString()
        weightUnit = intent.getStringExtra("wUnit").toString()
        heightUnit = intent.getStringExtra("hunit").toString()
        height = intent.getStringExtra("height").toString()
        binding.getfilter.setOnClickListener {
            binding.getfilter.setBackgroundResource(R.drawable.btn_outline_back)
            binding.getfilter.setTextColor(resources.getColor(R.color.appColor))
            binding.sleeping.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.sleeping.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.weight.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.weight.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.track.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.track.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.improvefitness.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.improvefitness.setTextColor(resources.getColor(R.color.smokey_grey))
            interest= "Get Fitter"
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
            binding.improvefitness.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.improvefitness.setTextColor(resources.getColor(R.color.smokey_grey))
            interest= "Better Sleeping Habits"

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
            binding.improvefitness.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.improvefitness.setTextColor(resources.getColor(R.color.smokey_grey))
            interest= "Weight Loss"

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
            binding.improvefitness.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.improvefitness.setTextColor(resources.getColor(R.color.smokey_grey))
            interest= "Track My Nutrition"

        }
        binding.improvefitness.setOnClickListener {
            binding.getfilter.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.getfilter.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.sleeping.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.sleeping.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.weight.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.weight.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.track.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.track.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.improvefitness.setBackgroundResource(R.drawable.btn_outline_back)
            binding.improvefitness.setTextColor(resources.getColor(R.color.appColor))
            interest= "Improve My Overall Fitness"

        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            if (interest == "")
            {
                utilities.showFailureToast(this@GoalActivity,"Please Select Your Goal")
            }else{
                val intentGoal = Intent(this@GoalActivity,AchieveGoalActivity::class.java)
                intentGoal.putExtra("gender",gender)
                intentGoal.putExtra("age",age)
                intentGoal.putExtra("wUnit",weightUnit)
                intentGoal.putExtra("weight",weight)
                intentGoal.putExtra("height",height)
                intentGoal.putExtra("hunit",heightUnit)
                intentGoal.putExtra("interest",interest)
                startActivity(intentGoal)
            }
        }

    }
}