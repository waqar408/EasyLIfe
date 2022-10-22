package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.userside.adapter.HowOldAdapter
import com.easylife.easylifes.databinding.ActivityHowOldBinding
import com.easylife.easylifes.model.HowOldModel
import com.easylife.easylifes.utils.Utilities

class HowOldActivity : AppCompatActivity(),HowOldAdapter.onClick {
    private lateinit var binding : ActivityHowOldBinding
    private lateinit var list : ArrayList<HowOldModel>
    var gender = ""
    var age = ""
    private lateinit var utils: Utilities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHowOldBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()



    }

    private fun initViews() {
        utils = Utilities(this@HowOldActivity)
        utils.setWhiteBars(this@HowOldActivity)
        list = ArrayList()
        val intent = intent
        gender = intent.getStringExtra("gender").toString()
        val nums = IntArray(100)
        for (i in nums.indices) {
            nums[i] = i + 1
            list.add(HowOldModel(nums[i].toString()))
        }
        binding.rv.layoutManager = LinearLayoutManager(this@HowOldActivity,
            RecyclerView.VERTICAL,false)
        binding.rv.adapter = HowOldAdapter(this@HowOldActivity,list,this)

        binding.layoutSend.setOnClickListener {
            if(age == "")
            {
                utils.showFailureToast(this@HowOldActivity,"Please select your age")
            }else{
                val intentWeight = Intent(this@HowOldActivity,WeightActivity::class.java)
                intentWeight.putExtra("gender",gender)
                intentWeight.putExtra("age",age)
                startActivity(intentWeight)
            }
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    override fun onClick(position: Int) {
        val model  = list[position]
        age =model.number
    }
}