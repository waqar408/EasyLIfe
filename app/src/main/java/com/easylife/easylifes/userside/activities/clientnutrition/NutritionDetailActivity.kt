package com.easylife.easylifes.userside.activities.clientnutrition


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.ActivityNutritionDetailBinding
import com.easylife.easylifes.model.mealtimes.MealTimesDataModel
import com.easylife.easylifes.model.mealtimes.MealTimesResponseModel
import com.easylife.easylifes.userside.adapter.NutritionDetailAdapter
import com.easylife.easylifes.utils.Utilities
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NutritionDetailActivity : AppCompatActivity()
    {
    private lateinit var binding : ActivityNutritionDetailBinding
    private lateinit var utilities: Utilities
    private lateinit var mealTimesList: ArrayList<MealTimesDataModel>
    var clientid = ""
    private var mealPlanId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
        getMealTimes(mealPlanId)

    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }


    }


    private fun initViews() {
        utilities = Utilities(this@NutritionDetailActivity)
        utilities.setGrayBar(this@NutritionDetailActivity)
        val intent = intent
        clientid = intent.getStringExtra("clientid").toString()
        mealPlanId = intent.getStringExtra("mealplanid").toString()

    }




    private fun getMealTimes(mealId: String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@NutritionDetailActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().mealTimes(
                "view", mealId,
                "",""
            )
                .enqueue(object : Callback<MealTimesResponseModel> {

                    override fun onResponse(
                        call: Call<MealTimesResponseModel>,
                        response: Response<MealTimesResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                mealTimesList = ArrayList()
                                mealTimesList = signupResponse.data
                                binding.rvClientNutrition.layoutManager =
                                    LinearLayoutManager(this@NutritionDetailActivity)
                                binding.rvClientNutrition.adapter =
                                    NutritionDetailAdapter(
                                        this@NutritionDetailActivity,
                                        mealTimesList)
                            } else {
                                utilities.showFailureToast(
                                    this@NutritionDetailActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@NutritionDetailActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<MealTimesResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@NutritionDetailActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@NutritionDetailActivity)
        }
    }







}