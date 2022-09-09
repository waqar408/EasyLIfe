package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.userside.activities.MainActivity
import com.easylife.easylifes.databinding.ActivityReadyToGoBinding
import com.easylife.easylifes.utils.Utilities

class ReadyToGoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityReadyToGoBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadyToGoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@ReadyToGoActivity)
        utilities.setGreenBar(this@ReadyToGoActivity)    }

    private fun onClicks() {
        binding.rlgetstarted.setOnClickListener {
            startActivity(Intent(this@ReadyToGoActivity,MainActivity::class.java))
        }    }
}