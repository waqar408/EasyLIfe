package com.easylife.easylifes.userside.activities.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.CommentAdapter
import com.easylife.easylifes.databinding.ActivityReviewListBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.utils.Utilities

class ReviewListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityReviewListBinding
    private lateinit var commentList : ArrayList<JobsDataModel>
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()

    }

    private fun initViews() {
        utilities = Utilities(this@ReviewListActivity)
        utilities.setGrayBar(this@ReviewListActivity)
        commentList = ArrayList()
        commentList.add(JobsDataModel(R.drawable.personprofileicon,"Luke Winnick"))
        commentList.add(JobsDataModel(R.drawable.personprofileicon,"Luke Winnick"))
        commentList.add(JobsDataModel(R.drawable.personprofileicon,"Luke Winnick"))
        commentList.add(JobsDataModel(R.drawable.personprofileicon,"Luke Winnick"))
        commentList.add(JobsDataModel(R.drawable.personprofileicon,"Luke Winnick"))
        commentList.add(JobsDataModel(R.drawable.personprofileicon,"Luke Winnick"))
        binding.rvComment.layoutManager  = LinearLayoutManager(this@ReviewListActivity,LinearLayoutManager.VERTICAL,false)
        binding.rvComment.adapter = CommentAdapter(this@ReviewListActivity,commentList)    }
}