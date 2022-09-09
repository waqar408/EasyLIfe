package com.easylife.easylifes.userside.activities.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.userside.activities.auth.ChangePasswordActivity
import com.easylife.easylifes.userside.activities.auth.LogoutActivity
import com.easylife.easylifes.databinding.ActivitySettingsBinding
import com.easylife.easylifes.utils.Utilities

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingsBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.lnChangePassword.setOnClickListener {
            startActivity(Intent(this@SettingsActivity,ChangePasswordActivity::class.java))
        }
        binding.lnSignout.setOnClickListener {
            startActivity(Intent(this@SettingsActivity,LogoutActivity::class.java))
        }
    }

    private fun initViews() {
        utilities = Utilities(this@SettingsActivity)
        utilities.setGrayBar(this@SettingsActivity)    }
}