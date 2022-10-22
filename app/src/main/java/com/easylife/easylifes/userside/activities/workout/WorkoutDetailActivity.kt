package com.easylife.easylifes.userside.activities.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.ActivityWorkoutDetailBinding
import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutsResponseModel
import com.easylife.easylifes.model.getuserworkouts.UserWorkoutVideoListModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.userside.adapter.WorkoutDetailAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutDetailActivity : AppCompatActivity() {
    var clientid = ""
    private var categoryid = ""
    private var categoryname = ""
    var trainerId = ""
    var position = ""
    var from  = ""
    val apiClient = ApiClient()
    var allVideoList: ArrayList<UserWorkoutVideoListModel> = ArrayList()
    var list1: ArrayList<UserWorkoutVideoListModel> = ArrayList()

    private lateinit var binding: ActivityWorkoutDetailBinding
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
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
        utilities = Utilities(this@WorkoutDetailActivity)
        utilities.setGrayBar(this@WorkoutDetailActivity)

        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            trainerId = java.lang.String.valueOf(obj.id)
        }
        val intent = intent
        clientid = intent.getStringExtra("clientid").toString()
        categoryid = intent.getStringExtra("categoryid").toString()
        categoryname = intent.getStringExtra("categoryName").toString()
        position = intent.getStringExtra("position").toString()

        binding.workoutCategoryName.text = categoryname

        userWorkoutDetail()
    }

    private fun userWorkoutDetail() {
        if (utilities.isConnectingToInternet(this@WorkoutDetailActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().getUserWorkoutDetail(
                clientid, trainerId
            )
                .enqueue(object : Callback<GetUserWorkoutsResponseModel> {

                    override fun onResponse(
                        call: Call<GetUserWorkoutsResponseModel>,
                        response: Response<GetUserWorkoutsResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                allVideoList = ArrayList()
                                list1= ArrayList()
                                list1.addAll(signupResponse.data.data[position.toInt()].user_workout_videos)
                                allVideoList.addAll(list1)
                                binding.rvVideos.layoutManager = LinearLayoutManager(
                                    this@WorkoutDetailActivity,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                binding.rvVideos.adapter = WorkoutDetailAdapter(
                                    this@WorkoutDetailActivity,
                                    list1
                                )
                            } else {
                                utilities.showFailureToast(
                                    this@WorkoutDetailActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@WorkoutDetailActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<GetUserWorkoutsResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@WorkoutDetailActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@WorkoutDetailActivity)
        }
    }



}