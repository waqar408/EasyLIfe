package com.easylife.easylifes.userside.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityMainBinding
import com.easylife.easylifes.utils.Utilities
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var utilities: Utilities
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()


    }

    private fun initViews() {
        val navController = findNavController(R.id.fragment_container)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

        utilities= Utilities(this@MainActivity)
        utilities.setGrayBar(this@MainActivity)    }

}