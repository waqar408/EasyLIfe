package com.easylife.easylifes.userside.activities.Support

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivitySupportBinding
import com.easylife.easylifes.utils.Utilities

class SupportActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupportBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@SupportActivity)
        utilities.setGrayBar(this@SupportActivity)
    }

    private fun onClicks() {
        binding.rlDeleteAccount.setOnClickListener {
            startActivity(Intent(this@SupportActivity, Support2Activity::class.java))
        }
    }
}