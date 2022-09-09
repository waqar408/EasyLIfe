package com.easylife.easylifes.userside.activities.choosepackage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.easylife.easylifes.databinding.ActivityAddNewCardBinding
import com.easylife.easylifes.utils.Utilities


class AddNewCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewCardBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewCardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@AddNewCardActivity)
        utilities.setGrayBar(this@AddNewCardActivity)
    }

    private fun onClicks() {
        binding.layoutSend.setOnClickListener {
            startActivity(Intent(this@AddNewCardActivity, PaymentSuccessActivity::class.java))
        }
    }
}