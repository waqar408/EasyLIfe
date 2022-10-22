package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityEmailOtpVerficationBinding
import com.easylife.easylifes.utils.Utilities

class EmailOtpVerficationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEmailOtpVerficationBinding
    private lateinit var utilities: Utilities
    private var otpCode = ""
    private var emailAddress  = ""
    private var edCode = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailOtpVerficationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }
    private fun initViews() {
        utilities = Utilities(this@EmailOtpVerficationActivity)
        utilities.setWhiteBars(this@EmailOtpVerficationActivity)
        val intent= intent
        otpCode = intent.getStringExtra("code").toString()
        emailAddress = intent.getStringExtra("email").toString()
        utilities.showSuccessToast(this@EmailOtpVerficationActivity,
            "Please Enter This Otp $otpCode"
        )


    }

    private fun onClicks() {

        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            edCode = binding.pinview.text.toString()
            if (edCode == "")
            {
                utilities.showFailureToast(this@EmailOtpVerficationActivity,"Please Enter Otp")
            }else if (edCode != otpCode)
            {
                utilities.showFailureToast(this@EmailOtpVerficationActivity,"You Entered Wrong Otp Code")
            }else{
                val intent = Intent(this@EmailOtpVerficationActivity,ResetPasswordActivity::class.java)
                intent.putExtra("email",emailAddress)
                startActivity(intent)
            }
        }
    }
}