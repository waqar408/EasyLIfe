package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityAchieveGoalBinding
import com.easylife.easylifes.utils.Utilities

class AchieveGoalActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAchieveGoalBinding
    var gender = ""
    var age = ""
    var weight = ""
    private var weightUnit = ""
    var height = ""
    private var heightUnit = ""
    private var interest = ""
    private var achieve = ""
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchieveGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        utilities = Utilities(this@AchieveGoalActivity)
        utilities.setWhiteBars(this@AchieveGoalActivity)
        val intent = intent
        gender = intent.getStringExtra("gender").toString()
        age = intent.getStringExtra("age").toString()
        weight = intent.getStringExtra("weight").toString()
        weightUnit = intent.getStringExtra("wUnit").toString()
        heightUnit = intent.getStringExtra("hunit").toString()
        height = intent.getStringExtra("height").toString()
        interest = intent.getStringExtra("interest").toString()
        binding.getfilter.setOnClickListener {
            binding.getfilter.setBackgroundResource(R.drawable.btn_outline_back)
            binding.getfilter.setTextColor(resources.getColor(R.color.appColor))
            binding.sleeping.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.sleeping.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.weight.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.weight.setTextColor(resources.getColor(R.color.smokey_grey))
            achieve= "Diet"
        }
        binding.sleeping.setOnClickListener {
            binding.getfilter.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.getfilter.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.sleeping.setBackgroundResource(R.drawable.btn_outline_back)
            binding.sleeping.setTextColor(resources.getColor(R.color.appColor))
            binding.weight.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.weight.setTextColor(resources.getColor(R.color.smokey_grey))
            achieve= "Workout"

        }
        binding.weight.setOnClickListener {
            binding.getfilter.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.getfilter.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.sleeping.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.sleeping.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.weight.setBackgroundResource(R.drawable.btn_outline_back)
            binding.weight.setTextColor(resources.getColor(R.color.appColor))
            achieve= "Workout and Diet"

        }
        binding.layoutSend.setOnClickListener {
            if (achieve == "")
            {
                utilities.showFailureToast(this@AchieveGoalActivity,"Please Let Us Know How You Want To Acheive This Goal")
            }else{
                val intents = Intent(this@AchieveGoalActivity,CurrentFitnessActivity::class.java)
                intents.putExtra("gender",gender)
                intents.putExtra("age",age)
                intents.putExtra("wUnit",weightUnit)
                intents.putExtra("weight",weight)
                intents.putExtra("height",height)
                intents.putExtra("hunit",heightUnit)
                intents.putExtra("interest",interest)
                intents.putExtra("goal",achieve)
                startActivity(intents)
            }
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }
}