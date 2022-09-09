package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityGetStartedBinding
import com.easylife.easylifes.utils.Utilities

class GetStartedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetStartedBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@GetStartedActivity)
        utilities.setWhiteBars(this@GetStartedActivity)
    }

    private fun onClicks() {
        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this@GetStartedActivity, LoginActivity::class.java)
            utilities.saveString(this@GetStartedActivity,"usertype","user")
            startActivity(intent)
        }
        binding.btnGetStartedAsTrainer.setOnClickListener {
            val intent = Intent(this@GetStartedActivity, LoginActivity::class.java)
            utilities.saveString(this@GetStartedActivity,"usertype","trainer")
            startActivity(intent)
        }
    }
}