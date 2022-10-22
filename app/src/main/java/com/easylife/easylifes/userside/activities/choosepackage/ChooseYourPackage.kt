package com.easylife.easylifes.userside.activities.choosepackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.ActivityChooseYourPackageBinding
import com.easylife.easylifes.model.trainerdetail.SubscriptionPackageDataModel
import com.easylife.easylifes.userside.adapter.PackageAdapter
import com.easylife.easylifes.utils.Utilities

class ChooseYourPackage : AppCompatActivity() {
    private lateinit var binding: ActivityChooseYourPackageBinding
    private lateinit var utilities: Utilities
    private lateinit var packageList: ArrayList<SubscriptionPackageDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseYourPackageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()


    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@ChooseYourPackage)
        utilities.setGrayBar(this@ChooseYourPackage)
        packageList=  ArrayList()
        val intent = intent
        packageList = intent.getParcelableArrayListExtra("packagelist")!!

        binding.rvPackages.layoutManager = LinearLayoutManager(this@ChooseYourPackage)
        binding.rvPackages.adapter = PackageAdapter(this@ChooseYourPackage,packageList)
    }


}