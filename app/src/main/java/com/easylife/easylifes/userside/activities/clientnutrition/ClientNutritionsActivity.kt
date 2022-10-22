package com.easylife.easylifes.userside.activities.clientnutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.easylife.easylifes.databinding.ActivityClientNutritionsBinding
import com.easylife.easylifes.model.mealplan.MealPlanResponseModel
import com.easylife.easylifes.model.mealplan.MealPlansDataModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.trainerside.adapter.AllNutritionsAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientNutritionsActivity : AppCompatActivity() , AllNutritionsAdapter.onAllClientDetailClick {
    private lateinit var binding : ActivityClientNutritionsBinding
    private lateinit var utilities: Utilities
    private lateinit var plansList: ArrayList<MealPlansDataModel>
    var clientId = ""
    var trainerId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientNutritionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClickss()
        allPlansApi()


    }

    private fun onClickss() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@ClientNutritionsActivity)
        utilities.setGrayBar(this@ClientNutritionsActivity)
        val intent = intent
        clientId = intent.getStringExtra("clientid").toString()
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@ClientNutritionsActivity, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        trainerId = obj.id.toString()




    }

    override fun onClickArea(position: Int) {
        val model = plansList[position]
        val intent = Intent(this@ClientNutritionsActivity, NutritionDetailActivity::class.java)
        intent.putExtra("mealplanid",model.id.toString())
        intent.putExtra("clientid", clientId)
        startActivity(intent)
        finish()
    }

    private fun allPlansApi(

    ) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@ClientNutritionsActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().viewMealPlan(
                "view", trainerId,
                clientId
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
                                plansList = ArrayList()
                                plansList = signupResponse.data
                                binding.rvAllNutritions.layoutManager =
                                    GridLayoutManager(this@ClientNutritionsActivity, 2)
                                binding.rvAllNutritions.adapter = AllNutritionsAdapter(
                                    this@ClientNutritionsActivity,
                                    plansList,
                                    this@ClientNutritionsActivity
                                )
                            } else {
                                utilities.showFailureToast(
                                    this@ClientNutritionsActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@ClientNutritionsActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<MealPlanResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@ClientNutritionsActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@ClientNutritionsActivity)
        }
    }




}