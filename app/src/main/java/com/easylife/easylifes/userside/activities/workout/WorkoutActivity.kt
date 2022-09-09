package com.easylife.easylifes.userside.activities.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.WorkoutAdapter
import com.easylife.easylifes.databinding.ActivityWorkoutBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.utils.Utilities

class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWorkoutBinding
    private lateinit var plansList : ArrayList<JobsDataModel>
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        plansData()
    }

    private fun initViews() {
        utilities = Utilities(this@WorkoutActivity)
        utilities.setGrayBar(this@WorkoutActivity)    }

    private fun plansData() {
        plansList = ArrayList()
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Leg Workout"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Workout # 2"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Workout # 3"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Nutrition"))
        binding.rvWorkout.layoutManager= GridLayoutManager(this@WorkoutActivity,
            2)
        binding.rvWorkout.adapter = WorkoutAdapter(this@WorkoutActivity,plansList)
    }
}