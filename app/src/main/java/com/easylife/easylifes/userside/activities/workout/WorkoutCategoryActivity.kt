package com.easylife.easylifes.userside.activities.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.WorkoutCategoriesAdapter
import com.easylife.easylifes.databinding.ActivityWorkoutCategoryBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.utils.Utilities

class WorkoutCategoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWorkoutCategoryBinding
    private lateinit var utilities: Utilities
    private lateinit var workoutCartegoryList : ArrayList<JobsDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        utilities = Utilities(this@WorkoutCategoryActivity)
        utilities.setGrayBar(this@WorkoutCategoryActivity)
        workoutCartegoryList = ArrayList()
        /*workoutCartegoryList.add(JobsDataModel(R.drawable.imgcategory,""))
        workoutCartegoryList.add(JobsDataModel(R.drawable.imgcategory,""))
        workoutCartegoryList.add(JobsDataModel(R.drawable.imgcategory,""))
        workoutCartegoryList.add(JobsDataModel(R.drawable.imgcategory,""))
        workoutCartegoryList.add(JobsDataModel(R.drawable.imgcategory,""))*/
        binding.rlWorkoutCategories.layoutManager = GridLayoutManager(this@WorkoutCategoryActivity,2)
        binding.rlWorkoutCategories.adapter = WorkoutCategoriesAdapter(this@WorkoutCategoryActivity,workoutCartegoryList)
    }
}