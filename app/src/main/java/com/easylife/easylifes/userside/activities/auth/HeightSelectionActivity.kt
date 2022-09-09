package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker.OnValueChangeListener
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
    var weightUnit = ""
    var heightft = ""
    var heightInch = ""
    var heightUnit = ""
    var heightCm = ""
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
        binding.firstNumber.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            val text = "Changed from $oldVal to $newVal"
            heightft = newVal.toString()
        })
        binding.secondNumber.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            val text = "Changed from $oldVal to $newVal"
            heightInch = newVal.toString()
        })
        binding.numbercm.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            val text = "Changed from $oldVal to $newVal"
            heightCm = newVal.toString()
        })
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            if (heightUnit.equals("ft")) {
                if (heightft.equals("0")||heightft.equals("")) {
                    utilities.showFailureToast(
                        this@HeightSelectionActivity,
                        "Please enter height greater than zero"
                    )
                } else {
                    heightft = heightft + "." + heightInch
                    val intent = Intent(this@HeightSelectionActivity, GoalActivity::class.java)
                    intent.putExtra("gender", gender)
                    intent.putExtra("age", age)
                    intent.putExtra("wUnit", weightUnit)
                    intent.putExtra("weight", weight)
                    intent.putExtra("height", heightft)
                    intent.putExtra("hunit", heightUnit)
                    startActivity(intent)
                }
            }
            else{
                    heightft = heightCm
                    if (heightft.equals("0")||heightft.equals(""))
                    {
                        utilities.showFailureToast(
                            this@HeightSelectionActivity,
                            "Please enter height greater than zero"
                        )
                    }else{
                        val intent = Intent(this@HeightSelectionActivity,GoalActivity::class.java)
                        intent.putExtra("gender",gender)
                        intent.putExtra("age",age)
                        intent.putExtra("wUnit",weightUnit)
                        intent.putExtra("weight",weight)
                        intent.putExtra("height",heightft)
                        intent.putExtra("hunit",heightUnit)
                        startActivity(intent)
                    }

            }
        }
    }
}