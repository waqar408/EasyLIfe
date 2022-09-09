package com.easylife.easylifes.userside.activities.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityFullImageWorkoutBinding

class FullImageWorkoutActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFullImageWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullImageWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}