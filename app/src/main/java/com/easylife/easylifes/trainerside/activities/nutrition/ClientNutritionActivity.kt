package com.easylife.easylifes.trainerside.activities.nutrition

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityClientNutritionBinding
import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.model.mealplan.FoodDataModel
import com.easylife.easylifes.model.mealplan.MealPlanResponseModel
import com.easylife.easylifes.model.mealplan.MealPlansDataModel
import com.easylife.easylifes.model.mealplan.MealTimeDataModel
import com.easylife.easylifes.model.mealtimes.MealTimeFoodsDataModel
import com.easylife.easylifes.model.mealtimes.MealTimesDataModel
import com.easylife.easylifes.model.mealtimes.MealTimesResponseModel
import com.easylife.easylifes.trainerside.adapter.UserNutritionDetailAdapter
import com.easylife.easylifes.userside.adapter.ClientNutritionAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClientNutritionActivity : AppCompatActivity(),
    UserNutritionDetailAdapter.onSelectedWorkoutClick,UserNutritionDetailAdapter.onAddMealClick {
    private lateinit var binding: ActivityClientNutritionBinding
    private lateinit var utilities: Utilities
    private lateinit var mealTimesList: ArrayList<MealTimesDataModel>
    var clientid = ""
    var mealPlanId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientNutritionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
        getMealTimes(mealPlanId)
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            val intent = Intent(this@ClientNutritionActivity, AllNutritionsActivity::class.java)
            intent.putExtra("clientid", clientid)
            startActivity(intent)
            finish()
        }
        binding.rlDelete.setOnClickListener {
            bottomDeleteMealPlan()
        }

        binding.layoutAddMeal.setOnClickListener {
            createNewNutrition()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@ClientNutritionActivity, AllNutritionsActivity::class.java)
        intent.putExtra("clientid", clientid)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        utilities = Utilities(this@ClientNutritionActivity)
        utilities.setGrayBar(this@ClientNutritionActivity)
        val intent = intent
        clientid = intent.getStringExtra("clientid").toString()
        mealPlanId = intent.getStringExtra("mealplanid").toString()

    }


    private fun deleteMealPlan(mealPlanId: String,deleteType : String,mealid : String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@ClientNutritionActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().deleteMeal(
                deleteType, mealPlanId,
                mealid
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
                                utilities.showSuccessToast(
                                    this@ClientNutritionActivity,
                                    signupResponse.message
                                )
                                Handler(Looper.myLooper()!!).postDelayed({
                                    val intent = Intent(
                                        this@ClientNutritionActivity,
                                        AllNutritionsActivity::class.java
                                    )
                                    intent.putExtra("clientid", clientid)
                                    startActivity(intent)
                                    finish()
                                }, 1000)

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

    private fun createNewNutrition() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_create_nutrition)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes = lp
        val layout_send = dialog.findViewById<RelativeLayout>(R.id.layout_send)
        val nutritionName = dialog.findViewById<EditText>(R.id.edNutritionName)
        val tv = dialog.findViewById<TextView>(R.id.tv)
        val tvHeading = dialog.findViewById<TextView>(R.id.tvHeading)
        tv.text = "Select a name for the meal plan"
        tvHeading.text = "Meal Plan Name"
        layout_send.setOnClickListener {
            val nutritionName1 = nutritionName.text.toString()
            if (nutritionName1.equals("")) {
                utilities.showFailureToast(
                    this@ClientNutritionActivity,
                    "Please enter meal plan name"
                )
            } else {
                addMealPlan(nutritionName1)
                dialog.dismiss()
            }


        }

        dialog.show()
    }

    private fun getMealTimes(mealId: String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@ClientNutritionActivity)) {
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
                            if (signupResponse?.status!!.equals(true)) {
                                mealTimesList = ArrayList()
                                mealTimesList = signupResponse.data
                                binding.rvClientNutrition.layoutManager =
                                    LinearLayoutManager(this@ClientNutritionActivity)
                                binding.rvClientNutrition.adapter =
                                    UserNutritionDetailAdapter(
                                        this@ClientNutritionActivity,
                                        mealTimesList, this@ClientNutritionActivity
                                    ,this@ClientNutritionActivity)
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

                    override fun onFailure(call: Call<MealTimesResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@ClientNutritionActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@ClientNutritionActivity)
        }
    }
    private fun addMealPlan(mealId: String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@ClientNutritionActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().mealTimes(
                "add", mealPlanId,
                mealId,""
            )
                .enqueue(object : Callback<MealTimesResponseModel> {

                    override fun onResponse(
                        call: Call<MealTimesResponseModel>,
                        response: Response<MealTimesResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!.equals(true)) {
                                getMealTimes(mealPlanId)
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

                    override fun onFailure(call: Call<MealTimesResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@ClientNutritionActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@ClientNutritionActivity)
        }
    }


    override fun onClickArea(position: Int) {
        val model = mealTimesList.get(position)
        bottomDeleteMeal(model.id.toString())
    }

    override fun onAddMealClick(position: Int) {
        val model = mealTimesList.get(position)
        val intent = Intent(this@ClientNutritionActivity,SearchMealActivity::class.java)
        intent.putExtra("mealplanid",model.user_meal_plan_id)
        intent.putExtra("mealtimeid",model.id.toString())
        intent.putExtra("mealname",model.meal_time)
        intent.putExtra("clientid",clientid)
        startActivity(intent)
        finish()
    }


    private fun bottomDeleteMealPlan() {
        val bottomSheetDialog : BottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(this@ClientNutritionActivity)
        bottomSheetDialog.setContentView(R.layout.bottom_delete)
        bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val canelBtn = bottomSheetDialog.findViewById<RelativeLayout>(R.id.layout_backArrow)
        val btnDone = bottomSheetDialog.findViewById<TextView>(R.id.btnDone)
        btnDone!!.setOnClickListener{
            deleteMealPlan(mealPlanId,"meal_plan","")
            bottomSheetDialog.dismiss()
        }

        canelBtn!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun bottomDeleteMeal(deleteMeal : String) {
        val bottomSheetDialog : BottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(this@ClientNutritionActivity)
        bottomSheetDialog.setContentView(R.layout.bottom_delete)
        bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val canelBtn = bottomSheetDialog.findViewById<RelativeLayout>(R.id.layout_backArrow)
        val btnDone = bottomSheetDialog.findViewById<TextView>(R.id.btnDone)
        val bottomName = bottomSheetDialog.findViewById<TextView>(R.id.bottomName)
        bottomName!!.text = "Delete Meal"
        btnDone!!.setOnClickListener{
            deleteMeal(deleteMeal)
            bottomSheetDialog.dismiss()
        }

        canelBtn!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun deleteMeal(mealTimeId : String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@ClientNutritionActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().mealTimes(
                "delete", "",
                "",mealTimeId
            )
                .enqueue(object : Callback<MealTimesResponseModel> {

                    override fun onResponse(
                        call: Call<MealTimesResponseModel>,
                        response: Response<MealTimesResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!.equals(true)) {
                                getMealTimes(mealPlanId)
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

                    override fun onFailure(call: Call<MealTimesResponseModel>, t: Throwable) {
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