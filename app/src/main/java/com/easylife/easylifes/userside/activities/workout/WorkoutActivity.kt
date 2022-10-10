package com.easylife.easylifes.userside.activities.workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityWorkoutBinding
import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutDataListModel
import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutsResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.userworkoutcategories.UserCategoryDataModel
import com.easylife.easylifes.trainerside.activities.clientdetail.UserWorkoutDetailActivity
import com.easylife.easylifes.trainerside.adapter.AllWorkoutsAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutActivity : AppCompatActivity(),AllWorkoutsAdapter.onAllWorkoutClick {
    private lateinit var binding : ActivityWorkoutBinding
    private lateinit var allWorkoutCategoriesList: ArrayList<GetUserWorkoutDataListModel>
    private lateinit var workoutCategoriesList: ArrayList<UserCategoryDataModel>
    var clientId = ""
    var useridd = ""
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
        getUserWorkouts()
    }
    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }

    }


    private fun initViews() {
        utilities = Utilities(this@WorkoutActivity)
        utilities.setGrayBar(this@WorkoutActivity)

        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@WorkoutActivity, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        useridd  = java.lang.String.valueOf(obj.id)

        val intent = intent
        clientId = intent.getStringExtra("clientid").toString()
    }

    private fun getUserWorkouts() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@WorkoutActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().getUserWorkouts(clientId,useridd).enqueue(object :
                Callback<GetUserWorkoutsResponseModel> {
                override fun onResponse(
                    call: Call<GetUserWorkoutsResponseModel>,
                    response: Response<GetUserWorkoutsResponseModel>
                ) {
                    binding.dotloader.visibility = View.GONE
                    val signupResponse = response.body()
                    if (signupResponse!!.status ==  true) {
                        //banner list data
                        //categories data
                        allWorkoutCategoriesList = ArrayList()
                        allWorkoutCategoriesList = response.body()!!.data.data
                        allClients(allWorkoutCategoriesList)
                    } else {
                        utilities.showFailureToast(this@WorkoutActivity, signupResponse.message)
                    }
                }
                override fun onFailure(call: Call<GetUserWorkoutsResponseModel>, t: Throwable) {
                    binding.dotloader.visibility = View.GONE
                }
            })
        } else {
            utilities.showFailureToast(this@WorkoutActivity, resources.getString(R.string.checkinternet))
        }
    }

    private fun allClients(categoriesList: ArrayList<GetUserWorkoutDataListModel>) {
        binding.rvAllWorkout.layoutManager = GridLayoutManager(this@WorkoutActivity, 2)
        binding.rvAllWorkout.adapter = AllWorkoutsAdapter(this, categoriesList,this@WorkoutActivity)

    }

    override fun onClickArea(position: Int) {
        val model = allWorkoutCategoriesList.get(position)
        val intent =  Intent(this@WorkoutActivity, WorkoutDetailActivity::class.java)
        intent.putExtra("clientid",clientId.toString())
        intent.putExtra("categoryid",model.id.toString())
        intent.putExtra("categoryName",model.title)
        intent.putExtra("position",position.toString())
        startActivity(intent)
        finish()
    }

}