package com.easylife.easylifes.userside.activities.follower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.FollowerAdapter
import com.easylife.easylifes.userside.adapter.FollowingAdapter
import com.easylife.easylifes.databinding.ActivityFollowerFollowingBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.utils.Utilities

class FollowerFollowingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFollowerFollowingBinding
    private lateinit var utilities: Utilities
    private lateinit var listFollowers : ArrayList<JobsDataModel>
    private lateinit var listFollowing : ArrayList<JobsDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowerFollowingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        utilities = Utilities(this@FollowerFollowingActivity)
        utilities.setGrayBar(this@FollowerFollowingActivity)
        binding.tvft.setOnClickListener {
            binding.tvft.setBackgroundResource(R.drawable.btn_weight_selection_back)
            binding.tvcm.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.tvft.setTextColor(resources.getColor(R.color.white))
            binding.tvcm.setTextColor(resources.getColor(R.color.graycolor))
            binding.rvFollowers.visibility = View.VISIBLE
            binding.rvFollowings.visibility = View.GONE


        }
        binding.tvcm.setOnClickListener {
            binding.tvft.setBackgroundResource(R.drawable.btn_transparent_back)
            binding.tvcm.setBackgroundResource(R.drawable.btn_weight_selection_back)
            binding.tvft.setTextColor(resources.getColor(R.color.graycolor))
            binding.tvcm.setTextColor(resources.getColor(R.color.white))
            binding.rvFollowings.visibility = View.VISIBLE
            binding.rvFollowers.visibility  =View.GONE

        }

        listFollowers = ArrayList()
        listFollowers.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowers.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowers.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowers.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowers.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowers.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowers.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowers.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowers.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowers.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))

        listFollowing = ArrayList()
        listFollowing.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowing.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowing.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowing.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowing.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowing.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))
        listFollowing.add(JobsDataModel(R.drawable.personprofileicon,"Lucy Wanda"))

        binding.rvFollowers.layoutManager = GridLayoutManager(this@FollowerFollowingActivity,2)
        binding.rvFollowers.adapter  = FollowerAdapter(this@FollowerFollowingActivity,listFollowers)

        binding.rvFollowings.layoutManager = LinearLayoutManager(this@FollowerFollowingActivity,LinearLayoutManager.VERTICAL,false)
        binding.rvFollowings.adapter = FollowingAdapter(this@FollowerFollowingActivity,listFollowing)
    }
}