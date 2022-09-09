package com.easylife.easylifes.userside.activities.choosepackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityPaymentBinding
import com.easylife.easylifes.utils.Utilities

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@PaymentActivity)
        utilities.setGrayBar(this@PaymentActivity)
    }

    private fun onClicks() {
        binding.tvUsePaymentMethod.setOnClickListener {
            startActivity(Intent(this@PaymentActivity, AddNewCardActivity::class.java))
        }
    }
}