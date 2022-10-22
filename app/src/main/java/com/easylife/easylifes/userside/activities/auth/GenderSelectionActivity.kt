package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityGenderSelectionBinding
import com.easylife.easylifes.utils.Utilities

class GenderSelectionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGenderSelectionBinding
    private lateinit var utils : Utilities
    var gender = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityGenderSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initViews()
        onClicks()

    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun initViews()
    {
        utils = Utilities(this@GenderSelectionActivity)
        utils.setWhiteBars(this@GenderSelectionActivity)
        binding.rlMale.setOnClickListener {
            binding.rlMale.setBackgroundResource(R.drawable.bg_green_rounded_30dp)
            binding.layoutFemale.setBackgroundResource(R.drawable.bg_gray_rounded_30dp)
            binding.tvMale.setTextColor(resources.getColor(R.color.black))
            binding.tvFemale.setTextColor(resources.getColor(R.color.smokey_grey))
            gender = "Male"
        }
        binding.layoutFemale.setOnClickListener {
            binding.rlMale.setBackgroundResource(R.drawable.bg_gray_rounded_30dp)
            binding.layoutFemale.setBackgroundResource(R.drawable.bg_green_rounded_30dp)
            binding.tvMale.setTextColor(resources.getColor(R.color.smokey_grey))
            binding.tvFemale.setTextColor(resources.getColor(R.color.black))
            gender = "Female"
        }

        binding.layoutSend.setOnClickListener {
            if (gender == "")
            {
                utils.showFailureToast(this@GenderSelectionActivity,"Please Select Gender")
            }else{
                val intent = Intent(this@GenderSelectionActivity,HowOldActivity::class.java)
                intent.putExtra("gender",gender)
                startActivity(intent)
            }
        }
    }
}