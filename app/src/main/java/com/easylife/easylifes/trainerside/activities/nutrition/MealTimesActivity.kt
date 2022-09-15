package com.easylife.easylifes.trainerside.activities.nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityMealTimesBinding
import com.easylife.easylifes.model.chat.inbox.MessagesResponseModel
import com.easylife.easylifes.model.mealplan.MealTimeDataModel
import com.easylife.easylifes.model.mealtime.MealTimesResponseModel
import com.easylife.easylifes.trainerside.adapter.MealTimesAdapter
import com.easylife.easylifes.utils.Utilities
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealTimesActivity : AppCompatActivity(),MealTimesAdapter.onMealTimeClick {
    private lateinit var binding : ActivityMealTimesBinding
    private lateinit var utilities : Utilities
    private lateinit var mealTimeList : ArrayList<MealTimeDataModel>
    var planId = ""
    var nutritionName = ""
    var clientid = ""
    var from = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealTimesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        utilities = Utilities(this@MealTimesActivity)
        utilities.setGrayBar(this@MealTimesActivity)
        val intent = intent
        planId = intent.getStringExtra("mealId").toString()
        clientid = intent.getStringExtra("clientid").toString()
        nutritionName = intent.getStringExtra("nutritionName").toString()
        from = intent.getStringExtra("from").toString()
        getMealTimes()

        binding.layoutBackArrow.setOnClickListener {
            val intent = Intent(this@MealTimesActivity,CreateNutrtionActivity::class.java)
            intent.putExtra("mealId",planId)
            intent.putExtra("nutritionName",nutritionName)
            intent.putExtra("clientid",clientid)
            intent.putExtra("from",from)
            finish()
        }
    }

    private fun getMealTimes() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@MealTimesActivity)) {
            val url = apiClient.BASE_URL + "get-meal-times"
            apiClient.getApiService().mealTime(url)
                .enqueue(object : Callback<MealTimesResponseModel> {

                    override fun onResponse(
                        call: Call<MealTimesResponseModel>,
                        response: Response<MealTimesResponseModel>
                    ) {
                        val signupResponse = response.body()
                        if (signupResponse!!.status == true) {
                            if (!signupResponse.data.equals("")) {

                                mealTimeList = ArrayList()
                                mealTimeList = signupResponse.data
                                binding.rvMealTime.layoutManager = LinearLayoutManager(this@MealTimesActivity)
                                binding.rvMealTime.adapter = MealTimesAdapter(this@MealTimesActivity,mealTimeList,this@MealTimesActivity)

                            } else {
                                utilities.showFailureToast(
                                    this@MealTimesActivity,
                                    "No Data Found"
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@MealTimesActivity, signupResponse.message
                            )
                            Log.d("mess", signupResponse.message)

                        }
                    }

                    override fun onFailure(call: Call<MealTimesResponseModel>, t: Throwable) {

                        utilities.showFailureToast(this@MealTimesActivity, t.message!!)
                        Log.d("mess", t.message.toString())

                    }


                })


        } else {

            utilities.showFailureToast(
                this@MealTimesActivity,
                resources.getString(R.string.checkinternet)
            )

        }

    }

    override fun onMealTimeClick(position: Int) {
        //nothing for now
        val model  = mealTimeList[position]
        val intent = Intent(this@MealTimesActivity,SearchMealActivity::class.java)
        intent.putExtra("mealtimeid",model.id.toString())
        intent.putExtra("planid",planId.toString())
        intent.putExtra("nutritionName",nutritionName)
        intent.putExtra("clientid",clientid)
        intent.putExtra("from",from)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@MealTimesActivity,CreateNutrtionActivity::class.java)
        intent.putExtra("mealId",planId)
        intent.putExtra("nutritionName",nutritionName)
        intent.putExtra("clientid",clientid)
        intent.putExtra("from",from)
        finish()
    }

}