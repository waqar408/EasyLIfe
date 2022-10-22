package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityHeightSelectionBinding
import com.easylife.easylifes.utils.Utilities


class HeightSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeightSelectionBinding
    private lateinit var utilities: Utilities
    var gender = ""
    var age = ""
    var weight = ""
    private var weightUnit = ""
    private var heightft = ""
    private var heightInch = ""
    private var heightUnit = ""
    private var heightCm = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeightSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        heightUnit = "ft"
    }

    private fun initViews() {
        utilities = Utilities(this@HeightSelectionActivity)
        utilities.setWhiteBars(this@HeightSelectionActivity)
        val intent = intent
        gender = intent.getStringExtra("gender").toString()
        age = intent.getStringExtra("age").toString()
        weight = intent.getStringExtra("weight").toString()
        weightUnit = intent.getStringExtra("wUnit").toString()
        binding.tvft.setOnClickListener {
            binding.tvft.setBackgroundResource(R.drawable.btn_weight_selection_white)
            binding.tvcm.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.tvft.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.tvcm.setTextColor(resources.getColor(R.color.black))
            binding.lnFt.visibility = View.VISIBLE
            binding.lncm.visibility = View.GONE
            heightUnit = "ft"


        }
        binding.tvcm.setOnClickListener {
            binding.tvft.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.tvcm.setBackgroundResource(R.drawable.btn_weight_selection_white)
            binding.tvft.setTextColor(resources.getColor(R.color.black))
            binding.tvcm.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.lnFt.visibility = View.GONE
            binding.lncm.visibility = View.VISIBLE
            heightUnit = "cm"
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.firstNumber.maxValue = 500
        binding.firstNumber.minValue = 0
        binding.secondNumber.maxValue = 500
        binding.secondNumber.minValue = 0
        binding.numbercm.minValue = 0
        binding.numbercm.maxValue = 500
        binding.firstNumber.setOnValueChangedListener { _, _, newVal ->
//            "Changed from $oldVal to $newVal"
            heightft = newVal.toString()
        }
        binding.secondNumber.setOnValueChangedListener { _, _, newVal ->
//            "Changed from $oldVal to $newVal"
            heightInch = newVal.toString()
        }
        binding.numbercm.setOnValueChangedListener { _, _, newVal ->
//            "Changed from $oldVal to $newVal"
            heightCm = newVal.toString()
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            if (heightUnit == "ft") {
                if (heightft == "0" || heightft == "") {
                    utilities.showFailureToast(
                        this@HeightSelectionActivity,
                        "Please enter height greater than zero"
                    )
                } else {
                    heightft = "$heightft.$heightInch"
                    val intentGoal = Intent(this@HeightSelectionActivity, GoalActivity::class.java)
                    intentGoal.putExtra("gender", gender)
                    intentGoal.putExtra("age", age)
                    intentGoal.putExtra("wUnit", weightUnit)
                    intentGoal.putExtra("weight", weight)
                    intentGoal.putExtra("height", heightft)
                    intentGoal.putExtra("hunit", heightUnit)
                    startActivity(intentGoal)
                }
            }
            else{
                    heightft = heightCm
                    if (heightft == "0" || heightft == "")
                    {
                        utilities.showFailureToast(
                            this@HeightSelectionActivity,
                            "Please enter height greater than zero"
                        )
                    }else{
                        val intentGoal = Intent(this@HeightSelectionActivity,GoalActivity::class.java)
                        intentGoal.putExtra("gender",gender)
                        intentGoal.putExtra("age",age)
                        intentGoal.putExtra("wUnit",weightUnit)
                        intentGoal.putExtra("weight",weight)
                        intentGoal.putExtra("height",heightft)
                        intentGoal.putExtra("hunit",heightUnit)
                        startActivity(intentGoal)
                    }

            }
        }
    }
}