package com.easylife.easylifes.userside.activities.address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityMyAddressBinding
import com.easylife.easylifes.utils.Utilities

class MyAddressActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyAddressBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }
    private fun initViews()
    {
        utilities = Utilities(this@MyAddressActivity)
        utilities.setGrayBar(this@MyAddressActivity)
    }
}