package com.easylife.easylifes.userside.activities.choosepackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityChooseYourPackageBinding
import com.easylife.easylifes.utils.Utilities

class ChooseYourPackage : AppCompatActivity() {
    private lateinit var binding : ActivityChooseYourPackageBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseYourPackageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()



    }

    private fun initViews() {
        utilities = Utilities(this@ChooseYourPackage)
        utilities.setWhiteBars(this@ChooseYourPackage)    }

    private fun onClicks() {
        binding.tvWorkoutPack.setOnClickListener {
            startActivity(Intent(this@ChooseYourPackage,PaymentActivity::class.java))
        }
    }
}