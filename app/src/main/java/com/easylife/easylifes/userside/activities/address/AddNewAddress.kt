package com.easylife.easylifes.userside.activities.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityAddNewAddressBinding
import com.easylife.easylifes.utils.Utilities

class AddNewAddress : AppCompatActivity() {
    private lateinit var binding : ActivityAddNewAddressBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()


    }
    private fun initViews()
    {
        utilities= Utilities(this@AddNewAddress)
        utilities.setGrayBar(this@AddNewAddress)
    }
    private fun onClicks()
    {
        binding.layoutSend.setOnClickListener {
            startActivity(Intent(this@AddNewAddress,MyAddressActivity::class.java))
        }
    }

}