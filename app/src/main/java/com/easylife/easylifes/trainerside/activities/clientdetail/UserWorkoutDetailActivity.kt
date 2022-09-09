package com.easylife.easylifes.trainerside.activities.clientdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.easylife.easylifes.databinding.ActivityUserWorkoutDetailBinding
import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutsResponseModel
import com.easylife.easylifes.model.getuserworkouts.UserWorkoutVideoListModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.model.workoutdetial.UserWorkoutDetailDataModel
import com.easylife.easylifes.model.workoutdetial.UserWorkoutDetailResponseModel
import com.easylife.easylifes.trainerside.activities.FullScreenVideoActivity
import com.easylife.easylifes.trainerside.adapter.UserWorkoutDetailVideoAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserWorkoutDetailActivity : AppCompatActivity(),
    UserWorkoutDetailVideoAdapter.onSelectedWorkoutClick {
    private lateinit var binding: ActivityUserWorkoutDetailBinding
    var clientid = ""
    var categoryid = ""
    var categoryimage = ""
    var categoryname = ""
    var trainerId = ""
    var position = ""
    private lateinit var utilities: Utilities
    val apiClient = ApiClient()
    private var videoList: ArrayList<UserWorkoutVideoListModel> = java.util.ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initViews()
        onClicks()

    }

    fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@UserWorkoutDetailActivity)
        utilities.setGrayBar(this@UserWorkoutDetailActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (!jsonn.isEmpty()) {
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
        if (utilities.isConnectingToInternet(this@UserWorkoutDetailActivity)) {
            apiClient.getApiService().getUserWorkoutDetail(
                trainerId, clientid
            )
                .enqueue(object : Callback<GetUserWorkoutsResponseModel> {

                    override fun onResponse(
                        call: Call<GetUserWorkoutsResponseModel>,
                        response: Response<GetUserWorkoutsResponseModel>
                    ) {
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!.equals(true)) {
                                val allVideoList: ArrayList<UserWorkoutVideoListModel> = ArrayList()
                                var list1: ArrayList<UserWorkoutVideoListModel> = ArrayList()
                                list1.addAll(signupResponse.data.data[position.toInt()].user_workout_videos)
                                allVideoList.addAll(list1)
                                binding.rvVideos.layoutManager = LinearLayoutManager(
                                    this@UserWorkoutDetailActivity,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                binding.rvVideos.adapter = UserWorkoutDetailVideoAdapter(
                                    this@UserWorkoutDetailActivity,
                                    list1,
                                    this@UserWorkoutDetailActivity
                                )
                            } else {
                                utilities.showFailureToast(
                                    this@UserWorkoutDetailActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@UserWorkoutDetailActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<GetUserWorkoutsResponseModel>, t: Throwable) {
                        // Error logging in
                        utilities.showFailureToast(this@UserWorkoutDetailActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@UserWorkoutDetailActivity)
        }
    }

    override fun onClickArea(position: Int) {
        val model = videoList.get(position)
        val intent = Intent(this@UserWorkoutDetailActivity, FullScreenVideoActivity::class.java)
        intent.putExtra("videourl", model.media)
        startActivity(intent)
    }
}