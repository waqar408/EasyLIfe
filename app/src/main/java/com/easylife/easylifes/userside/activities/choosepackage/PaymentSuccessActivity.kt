package com.easylife.easylifes.userside.activities.choosepackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityPaymentSuccessBinding
import com.easylife.easylifes.utils.Utilities

class PaymentSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentSuccessBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()


    }

    private fun initViews() {
        utilities = Utilities(this@PaymentSuccessActivity)
        utilities.setLightGreenBar(this@PaymentSuccessActivity)

        binding.layoutSend.setOnClickListener {
            finish()
        }
    }
}