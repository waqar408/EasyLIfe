package com.easylife.easylifes.trainerside.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityTrianerMainBinding
import com.easylife.easylifes.utils.Utilities
import com.google.android.material.bottomnavigation.BottomNavigationView

class TrainerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrianerMainBinding
    private lateinit var utilities: Utilities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrianerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        val navController = findNavController(R.id.fragment_container)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)
        utilities= Utilities(this@TrainerMainActivity)
        utilities.setGrayBar(this@TrainerMainActivity)    }
}