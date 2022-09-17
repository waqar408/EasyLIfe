package com.easylife.easylifes.trainerside.activities.nutrition

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.ActivityClientNutritionBinding
import com.easylife.easylifes.model.mealplan.FoodDataModel
import com.easylife.easylifes.model.mealplan.MealPlanResponseModel
import com.easylife.easylifes.model.mealplan.MealPlansDataModel
import com.easylife.easylifes.model.mealplan.MealTimeDataModel
import com.easylife.easylifes.userside.adapter.ClientNutritionAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClientNutritionActivity : AppCompatActivity(), ClientNutritionAdapter.onFoodClick {
    private lateinit var utilities: Utilities
    private lateinit var binding: ActivityClientNutritionBinding
    private lateinit var modelPlan: MealPlansDataModel
    private lateinit var mealTimesList: ArrayList<MealTimeDataModel>
    var clientid = ""
    var planid = ""
    var mealtimeid = ""
    var mealPlanId = ""
    var from = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientNutritionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.rlDelete.setOnClickListener {
            deleteMeal(mealPlanId)
        }
        binding.layoutMacronutritions.setOnClickListener {
            val gson = Gson()
            val mySelectMeal = gson.toJson(modelPlan)
            val intent = Intent(this@ClientNutritionActivity,MacronutrientsDetailActivity::class.java)
            intent.putExtra("myplan",mySelectMeal)
            startActivity(intent)
        }
    }

    private fun initViews() {
        utilities = Utilities(this@ClientNutritionActivity)
        utilities.setGrayBar(this@ClientNutritionActivity)
        val intent = intent
        val gson = Gson()
        modelPlan = gson.fromJson(intent.getStringExtra("myplan"), MealPlansDataModel::class.java)
        clientid = intent.getStringExtra("clientid").toString()
        from = intent.getStringExtra("from").toString()
        mealPlanId = intent.getStringExtra("mealplanid").toString()
        planid = modelPlan.id.toString()

        mealTimesList = ArrayList()
        var mealTimesLists: ArrayList<FoodDataModel> = ArrayList()
        var calories = 0.0
        var protein = 0.0
        var carbs = 0.0
        var fiber = 0.0
        var fat = 0.0
        var sodium = 0.0
        var quantity = 0.0
        mealTimesList = modelPlan.meal_times
        if (mealTimesList.size > 0) {
            for (i in 0 until mealTimesList.size) {

                mealTimesLists = mealTimesList.get(i).foods
                if (mealTimesLists.size > 0) {
                    for (j in 0 until mealTimesLists.size) {
                        calories = (mealTimesLists[j].food_details.meal_calories.toDouble() * mealTimesLists[j].food_details.serving_quantity.toDouble()) + calories
                        protein = (mealTimesLists[j].food_details.meal_protien.toDouble()  * mealTimesLists[j].food_details.serving_quantity.toDouble())+ protein
                        carbs = (mealTimesLists[j].food_details.meal_carbs.toDouble() * mealTimesLists[j].food_details.serving_quantity.toDouble()) + carbs
                        fiber = (mealTimesLists[j].food_details.meal_fibre.toDouble() * mealTimesLists[j].food_details.serving_quantity.toDouble()) + fiber
                        fat = (mealTimesLists[j].food_details.meal_fat.toDouble() * mealTimesLists[j].food_details.serving_quantity.toDouble()) + fat
                        sodium = (mealTimesLists[j].food_details.meal_sodium.toDouble() * mealTimesLists[j].food_details.serving_quantity.toDouble()) + sodium
                    }
                }
            }
        }

        val caloriess = calories.toString()
        val proteins = protein.toString()
        val carbss = carbs.toString()
        val fibers = fiber.toString()
        val fatss = fat.toString()
        val sodiums = sodium.toString()
        val separated1 = caloriess.split(".").toTypedArray()
        val separated2 = proteins.split(".").toTypedArray()
        val separated3 = carbss.split(".").toTypedArray()
        val separated4 = fibers.split(".").toTypedArray()
        val separated5 = fatss.split(".").toTypedArray()
        val separated6 = sodiums.split(".").toTypedArray()


        binding.rvClientNutrition.layoutManager = LinearLayoutManager(this@ClientNutritionActivity)
        binding.rvClientNutrition.adapter = ClientNutritionAdapter(this@ClientNutritionActivity,mealTimesList, this@ClientNutritionActivity)
        binding.tvCalories.text = separated1[0]
        binding.tvProtein.text = separated2[0]
        binding.tvCarbs.text = separated3[0]
        binding.tvFiber.text = separated4[0]
        binding.tvfat.text = separated5[0]
        binding.tvSodium.text = separated6[0]

    }

    override fun onFoodClick(position: Int) {
        val model = mealTimesList[position]
        val gson = Gson()
        val mySelectMeal = gson.toJson(model)
        val intent = Intent(this@ClientNutritionActivity, NutritionFoodDetailActivity::class.java)
        intent.putExtra("foodlist", mySelectMeal)
        intent.putExtra("clientid",clientid)
        intent.putExtra("mealId",planid.toString())
        intent.putExtra("mealtimeid",model.id.toString())
        intent.putExtra("nutritionName",model.meal_time)
        intent.putExtra("from",from)
        startActivity(intent)
        finish()

    }
    private fun deleteMeal(mealId: String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@ClientNutritionActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().deleteMeal(
                "meal_plan", mealId,
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
                            if (signupResponse?.status!!.equals(true)) {
                                utilities.showSuccessToast(this@ClientNutritionActivity,signupResponse.message)
                                Handler(Looper.myLooper()!!).postDelayed({
                                    finish()
                                },1000)

                            } else {
                                utilities.showFailureToast(
                                    this@ClientNutritionActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@ClientNutritionActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<MealPlanResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@ClientNutritionActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@ClientNutritionActivity)
        }
    }

}