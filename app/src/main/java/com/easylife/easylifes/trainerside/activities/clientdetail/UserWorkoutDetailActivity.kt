package com.easylife.easylifes.trainerside.activities.clientdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.ActivityUserWorkoutDetailBinding
import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutsResponseModel
import com.easylife.easylifes.model.getuserworkouts.UserWorkoutVideoListModel
import com.easylife.easylifes.model.mealplan.MealPlanResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
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
    private var categoryid = ""
    private var categoryname = ""
    var trainerId = ""
    var position = ""
    var from  = ""
    private lateinit var utilities: Utilities
    val apiClient = ApiClient()
    var allVideoList: ArrayList<UserWorkoutVideoListModel> = ArrayList()
    var list1: ArrayList<UserWorkoutVideoListModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initViews()
        onClicks()

    }

    fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            val intent = Intent(this@UserWorkoutDetailActivity,AllWorkoutsActivity::class.java)
            intent.putExtra("clientid",clientid)
            startActivity(intent)
            finish()
        }
        binding.layoutDelete.setOnClickListener {
            deleteWorkoutCollection(categoryid)
        }
        binding.layoutAdd.setOnClickListener {
            val intent = Intent(this@UserWorkoutDetailActivity,WorkoutSelectionActivity::class.java)
            intent.putExtra("categoryid",categoryid)
            intent.putExtra("from",from)
            intent.putExtra("clientid",clientid)
            intent.putExtra("categoryName",categoryname)
            intent.putExtra("position",position)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@UserWorkoutDetailActivity,AllWorkoutsActivity::class.java)
        intent.putExtra("clientid",clientid)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        utilities = Utilities(this@UserWorkoutDetailActivity)
        utilities.setGrayBar(this@UserWorkoutDetailActivity)
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
        from = intent.getStringExtra("from").toString()
        position = intent.getStringExtra("position").toString()

        binding.workoutCategoryName.text = categoryname

        userWorkoutDetail()
    }

    private fun userWorkoutDetail() {
        if (utilities.isConnectingToInternet(this@UserWorkoutDetailActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().getUserWorkoutDetail(
                trainerId, clientid
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
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@UserWorkoutDetailActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@UserWorkoutDetailActivity)
        }
    }

    override fun onClickArea(position: Int) {
        val model = list1[position]
        deleteWorkoutVideo(model.id.toString())
    }

    private fun deleteWorkoutVideo(mealId: String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@UserWorkoutDetailActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().deleteWorkout(
                "workout_video", "",
                mealId
            )
                .enqueue(object : Callback<MealPlanResponseModel> {

                    override fun onResponse(
                        call: Call<MealPlanResponseModel>,
                        response: Response<MealPlanResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                utilities.showSuccessToast(this@UserWorkoutDetailActivity,signupResponse.message)
                                Handler(Looper.myLooper()!!).postDelayed({
                                    val intent = Intent(this@UserWorkoutDetailActivity,AllWorkoutsActivity::class.java)
                                    intent.putExtra("clientid",clientid)
                                    startActivity(intent)
                                    finish()
                                },1000)

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

                    override fun onFailure(call: Call<MealPlanResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@UserWorkoutDetailActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@UserWorkoutDetailActivity)
        }
    }
    private fun deleteWorkoutCollection(mealId: String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@UserWorkoutDetailActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().deleteWorkout(
                "workout_collection", mealId,
                ""
            )
                .enqueue(object : Callback<MealPlanResponseModel> {

                    override fun onResponse(
                        call: Call<MealPlanResponseModel>,
                        response: Response<MealPlanResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                utilities.showSuccessToast(this@UserWorkoutDetailActivity,signupResponse.message)
                                Handler(Looper.myLooper()!!).postDelayed({
                                    val intent = Intent(this@UserWorkoutDetailActivity,AllWorkoutsActivity::class.java)
                                    intent.putExtra("clientid",clientid)
                                    startActivity(intent)
                                    finish()
                                },1000)

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

                    override fun onFailure(call: Call<MealPlanResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@UserWorkoutDetailActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@UserWorkoutDetailActivity)
        }
    }

}