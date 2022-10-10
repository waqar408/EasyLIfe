package com.easylife.easylifes.trainerside.activities.nutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityCreateNutrtionBinding
import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutsResponseModel
import com.easylife.easylifes.model.getuserworkouts.UserWorkoutVideoListModel
import com.easylife.easylifes.model.mealplan.CreateMealPlanResponseModel
import com.easylife.easylifes.model.mealplan.MealPlanResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.userside.activities.MainActivity
import com.easylife.easylifes.userside.activities.auth.GenderSelectionActivity
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateNutrtionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNutrtionBinding
    private lateinit var utilities: Utilities
    var nutritionName = ""
    var calories = ""
    var protein = ""
    var carbs = ""
    var fat = ""
    var fiber = ""
    var sodium = ""
    var sugar = ""
    var clientId = ""
    var trainerId = ""
    var from = ""
    val apiClient = ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNutrtionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }

    private fun onClicks() {

        binding.layoutBackArrow.setOnClickListener {
            val intent = Intent(this@CreateNutrtionActivity, AllNutritionsActivity::class.java)
            intent.putExtra("clientid",clientId)
            startActivity(intent)
            finish()
        }
        binding.layoutCreateWorkout.setOnClickListener { 
            calories = binding.edCalories.text.toString()
            protein = binding.edProtein.text.toString()
            carbs  = binding.edCarbs.text.toString()
            fat = binding.edFat.text.toString()
            fiber = binding.edFiber.text.toString()
            sodium = binding.edSodium.text.toString()
            sugar = binding.edSugar.text.toString()
            if (calories.equals(""))
            {
                utilities.showFailureToast(this@CreateNutrtionActivity,"Please Enter Calories")
            }else if (protein.equals(""))
            {
                utilities.showFailureToast(this@CreateNutrtionActivity,"Please Enter Protein")
            }else if (carbs.equals(""))
            {
                utilities.showFailureToast(this@CreateNutrtionActivity,"Please Enter Carbs")
            }else if (fat.equals(""))
            {
                utilities.showFailureToast(this@CreateNutrtionActivity,"Please Enter Fat")
            }else if (fiber.equals(""))
            {
                utilities.showFailureToast(this@CreateNutrtionActivity,"Please Enter Fiber")
            }else if (sodium.equals(""))
            {
                utilities.showFailureToast(this@CreateNutrtionActivity,"Please Enter Sodium")
            }else if (sugar.equals(""))
            {
                utilities.showFailureToast(this@CreateNutrtionActivity,"Please Enter Sugar")
            }else{
                createMacroNutrition(calories,protein,carbs,fat,fiber,sodium,sugar)
            }
        }

    }

    private fun createMacroNutrition(
        calories: String,
        protein: String,
        carbs: String,
        fat: String,
        fiber: String,
        sodium: String,
        sugar: String
    ) {
        if (utilities.isConnectingToInternet(this@CreateNutrtionActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().mealPlan("create", "",clientId,
                trainerId,nutritionName,calories,
                "",protein,carbs,fat,
                fiber,sodium,sugar)
                .enqueue(object : Callback<CreateMealPlanResponseModel> {

                    override fun onResponse(
                        call: Call<CreateMealPlanResponseModel>,
                        response: Response<CreateMealPlanResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!.equals(true)) {
                                utilities.showSuccessToast(this@CreateNutrtionActivity,signupResponse.message)
                                Handler(Looper.myLooper()!!).postDelayed({
                                    val intent = Intent(this@CreateNutrtionActivity, AllNutritionsActivity::class.java)
                                    intent.putExtra("clientid",clientId)
                                    startActivity(intent)
                                    finish()
                                },800)

                            } else {
                                utilities.showFailureToast(
                                    this@CreateNutrtionActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@CreateNutrtionActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<CreateMealPlanResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@CreateNutrtionActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@CreateNutrtionActivity)
        }
    }

    private fun initViews() {
        utilities = Utilities(this@CreateNutrtionActivity)
        utilities.setGrayBar(this@CreateNutrtionActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@CreateNutrtionActivity, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        trainerId  = obj.id.toString()
        val intent = intent
        nutritionName = intent.getStringExtra("nutritionName").toString()
        clientId = intent.getStringExtra("clientid").toString()
        binding.tvNames.text = nutritionName
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@CreateNutrtionActivity, AllNutritionsActivity::class.java)
        intent.putExtra("clientid",clientId)
        startActivity(intent)
        finish()
    }
}
