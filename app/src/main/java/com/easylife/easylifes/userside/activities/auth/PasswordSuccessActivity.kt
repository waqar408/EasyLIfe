package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityPasswordSuccessBinding
import com.easylife.easylifes.utils.Utilities

class PasswordSuccessActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPasswordSuccessBinding
    private lateinit var utilities : Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()


    }


    private fun onClicks() {
        binding.layoutSend.setOnClickListener {
            startActivity(Intent(this@PasswordSuccessActivity,GetStartedActivity::class.java))
            finish()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@PasswordSuccessActivity)
        utilities.setLightGreenBar(this@PasswordSuccessActivity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@PasswordSuccessActivity,GetStartedActivity::class.java)
        startActivity(intent)
        finish()
    }

}