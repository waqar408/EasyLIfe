package com.easylife.easylifes.userside.activities.Support

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivitySupport2Binding
import com.easylife.easylifes.utils.Utilities

class Support2Activity : AppCompatActivity() {
    private lateinit var binding :ActivitySupport2Binding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupport2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()


    }

    private fun initViews() {
        utilities = Utilities(this@Support2Activity)
        utilities.setGrayBar(this@Support2Activity)    }
}