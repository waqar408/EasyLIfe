package com.easylife.easylifes.userside.activities.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.NotificationAdapter
import com.easylife.easylifes.databinding.ActivityNotificationActiivtyBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.utils.Utilities

class NotificationActiivty : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationActiivtyBinding
    private lateinit var notificationList : ArrayList<JobsDataModel>
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationActiivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()


    }

    private fun initViews() {
        utilities = Utilities(this@NotificationActiivty)
        utilities.setGrayBar(this@NotificationActiivty)
        notificationList = ArrayList()
        notificationList.add(JobsDataModel(R.drawable.notificationicon,"Luke Winnick"))
        notificationList.add(JobsDataModel(R.drawable.notificationicon,"Luke Winnick"))
        notificationList.add(JobsDataModel(R.drawable.notificationicon,"Luke Winnick"))
        notificationList.add(JobsDataModel(R.drawable.notificationicon,"Luke Winnick"))
        notificationList.add(JobsDataModel(R.drawable.notificationicon,"Luke Winnick"))
        notificationList.add(JobsDataModel(R.drawable.notificationicon,"Luke Winnick"))
        notificationList.add(JobsDataModel(R.drawable.notificationicon,"Luke Winnick"))
        binding.rvNotification.layoutManager = LinearLayoutManager(this@NotificationActiivty,LinearLayoutManager.VERTICAL,false)
        binding.rvNotification.adapter = NotificationAdapter(this@NotificationActiivty,notificationList)    }
}