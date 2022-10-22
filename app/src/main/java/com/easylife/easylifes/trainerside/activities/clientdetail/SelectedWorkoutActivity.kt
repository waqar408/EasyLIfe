package com.easylife.easylifes.trainerside.activities.clientdetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.ActivitySelectedWorkoutBinding
import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.trainerside.adapter.SelectedWorkoutAdapter
import com.easylife.easylifes.utils.Utilities
import com.easylife.easylifes.utils.UtilityClass
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient


class SelectedWorkoutActivity : AppCompatActivity(), SelectedWorkoutAdapter.onCategoryVideoClick {
    private lateinit var binding: ActivitySelectedWorkoutBinding
    private lateinit var utilities: Utilities
    private var allClientsList: ArrayList<AllWorkoutsDataListModel> = ArrayList()
    private var tvWorkoutCategory = ""
    var clientid = ""
    var userId = ""
    private var categoryid = ""
    var from = ""
    var position= ""
    lateinit var adapter: SelectedWorkoutAdapter
    lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClick()
    }


    private fun onClick() {

        binding.layoutBackArrow.setOnClickListener {
            val intent = Intent(this@SelectedWorkoutActivity, WorkoutSelectionActivity::class.java)
            intent.putExtra("clientid",clientid)
            intent.putExtra("categoryName",tvWorkoutCategory)
            intent.putExtra("categoryid",categoryid)
            intent.putExtra("from",from)
            intent.putExtra("position",position)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@SelectedWorkoutActivity, WorkoutSelectionActivity::class.java)
        intent.putExtra("clientid",clientid)
        intent.putExtra("categoryName",tvWorkoutCategory)
        intent.putExtra("categoryid",categoryid)
        intent.putExtra("from",from)
        intent.putExtra("position",position)
        startActivity(intent)
        finish()

    }


    private fun initViews() {
        utilities = Utilities(this@SelectedWorkoutActivity)
        utilities.setGrayBar(this@SelectedWorkoutActivity)
        apiClient = ApiClient()
        adapter = SelectedWorkoutAdapter(
            this@SelectedWorkoutActivity,
            allClientsList,
            this@SelectedWorkoutActivity
        )
        val intent = intent
        allClientsList = UtilityClass.instance!!.list
        tvWorkoutCategory = intent.getStringExtra("categoryName").toString()
        clientid = intent.getStringExtra("clientid").toString()
        categoryid = intent.getStringExtra("categoryid").toString()
        from = intent.getStringExtra("from").toString()
        position = intent.getStringExtra("position").toString()
        binding.tvWorkoutCategory.text = tvWorkoutCategory
        Log.d("allclientlist", allClientsList.toString())

        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = obj.id.toString()

        }


        setDataToRecyclerview()

        binding.layoutCreateWorkout.setOnClickListener {
            val intentList = Intent(this@SelectedWorkoutActivity, WorkoutsListActivity::class.java)
            intentList.putExtra("clientid",clientid)
            intentList.putExtra("categoryName",tvWorkoutCategory)
            intentList.putExtra("categoryid",categoryid)
            intentList.putExtra("from",from)
            intentList.putExtra("position",position)
            startActivity(intentList)
            finish()
        }
    }




    private fun setDataToRecyclerview() {
        binding.rvSelectedWorkout.layoutManager =
            LinearLayoutManager(this@SelectedWorkoutActivity, LinearLayoutManager.VERTICAL, false)
        adapter = SelectedWorkoutAdapter(
            this@SelectedWorkoutActivity,
            allClientsList,
            this@SelectedWorkoutActivity
        )
        binding.rvSelectedWorkout.adapter = adapter
    }

    override fun onClickArea(position: Int, holder: SelectedWorkoutAdapter.ViewHolder) {
//        val mode = allClientsList.get(position)

        allClientsList.removeAt(position)
        adapter.notifyItemRemoved(position)
        adapter.notifyDataSetChanged()
        if (allClientsList.size == 0) {
            binding.tvNoViewFound.visibility = View.VISIBLE
        }


    }


}
