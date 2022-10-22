package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityWeightBinding
import com.easylife.easylifes.utils.Utilities

class WeightActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWeightBinding
    private lateinit var utilities: Utilities
    var gender = ""
    var age = ""
    var weight = ""
    private var weightUnit = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        weightUnit = "lbs"
    }

    private fun initViews() {
        utilities = Utilities(this@WeightActivity)
        utilities.setWhiteBars(this@WeightActivity)
        val intent = intent
        gender = intent.getStringExtra("gender").toString()
        age = intent.getStringExtra("age").toString()
        binding.viewRuler.setOnRulerEvent {
            binding.tv.text = binding.viewRuler.currentValue.toString()
        }
        binding.tvLbs.setOnClickListener {
            binding.tvLbs.setBackgroundResource(R.drawable.btn_weight_selection_white)
            binding.tvKg.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.tvLbs.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.tvKg.setTextColor(resources.getColor(R.color.black))
            binding.tvUnit.text = "lbs"
            weightUnit = "lbs"
        }
        binding.tvKg.setOnClickListener {
            binding.tvLbs.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.tvKg.setBackgroundResource(R.drawable.btn_weight_selection_white)
            binding.tvLbs.setTextColor(resources.getColor(R.color.black))
            binding.tvKg.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.tvUnit.text = "kg"
            weightUnit = "kg"

        }
        binding.layoutSend.setOnClickListener {
            weight = binding.tv.text.toString()
            if (weight == "0" || weight == "")
            {
                utilities.showFailureToast(this@WeightActivity,"Please Select Weight")
            }else{
                val intentHeight = Intent(this@WeightActivity,HeightSelectionActivity::class.java)
                intentHeight.putExtra("gender",gender)
                intentHeight.putExtra("age",age)
                intentHeight.putExtra("wUnit",weightUnit)
                intentHeight.putExtra("weight",weight)
                startActivity(intentHeight)
            }
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }
}