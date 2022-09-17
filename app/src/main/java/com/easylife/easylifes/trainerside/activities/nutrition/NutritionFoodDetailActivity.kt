package com.easylife.easylifes.trainerside.activities.nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.ActivityNutritionFoodDetailBinding
import com.easylife.easylifes.model.mealplan.FoodDataModel
import com.easylife.easylifes.model.mealplan.MealPlanResponseModel
import com.easylife.easylifes.model.mealplan.MealTimeDataModel
import com.easylife.easylifes.trainerside.adapter.NutritionFoodDetailAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NutritionFoodDetailActivity : AppCompatActivity(),
    NutritionFoodDetailAdapter.onAllClientDetailClick {
    private lateinit var binding: ActivityNutritionFoodDetailBinding
    private lateinit var utilities: Utilities
    lateinit var mealtimemodel: MealTimeDataModel
    var clientid = ""
    var mealPlanId = ""
    var mealtimeid = ""
    var from = ""
    var nutritionName = ""
    private lateinit var list: ArrayList<FoodDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initViews()
        onClicks()


    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.rlAddNutrition.setOnClickListener {
            val intent = Intent(this@NutritionFoodDetailActivity, SearchMealActivity::class.java)
            intent.putExtra("clientid", clientid)
            intent.putExtra("planid", mealPlanId)
            intent.putExtra("mealtimeid", mealtimeid)
            intent.putExtra("from", from)
            intent.putExtra("nutritionName", nutritionName)
            startActivity(intent)
            finish()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@NutritionFoodDetailActivity)
        utilities.setWhiteBars(this@NutritionFoodDetailActivity)
        list = ArrayList()
        val intent = intent
        val gsonn = Gson()
        clientid = intent.getStringExtra("clientid").toString()
        mealPlanId = intent.getStringExtra("mealId").toString()
        mealtimeid = intent.getStringExtra("mealtimeid").toString()
        nutritionName = intent.getStringExtra("nutritionName").toString()
        val jsonn: String = intent.getStringExtra("foodlist").toString()
        if (!jsonn.isEmpty()) {

            val mealtimemodel = gsonn.fromJson(jsonn, MealTimeDataModel::class.java)
            list = mealtimemodel.foods
        }

        binding.rvNutritionFood.layoutManager =
            LinearLayoutManager(this@NutritionFoodDetailActivity)
        binding.rvNutritionFood.adapter =
            NutritionFoodDetailAdapter(this@NutritionFoodDetailActivity, list, this)
    }

    override fun onClickArea(position: Int) {
        val model = list.get(position)
        deleteMeal(model.id.toString())
        //nothing for now
    }

    private fun deleteMeal(mealId: String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@NutritionFoodDetailActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().deleteMeal(
                "meal", "",
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
                            if (signupResponse?.status!!.equals(true)) {
                                utilities.showSuccessToast(this@NutritionFoodDetailActivity,signupResponse.message)
                                Handler(Looper.myLooper()!!).postDelayed({
                                      finish()
                                },1000)

                            } else {
                                utilities.showFailureToast(
                                    this@NutritionFoodDetailActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@NutritionFoodDetailActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<MealPlanResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@NutritionFoodDetailActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@NutritionFoodDetailActivity)
        }
    }
}