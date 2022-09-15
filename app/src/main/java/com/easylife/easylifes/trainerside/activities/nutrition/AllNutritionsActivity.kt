package com.easylife.easylifes.trainerside.activities.nutrition

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityAllNutritionsBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.mealplan.CreateMealPlanResponseModel
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

class AllNutritionsActivity : AppCompatActivity(), AllNutritionsAdapter.onAllClientDetailClick {
    private lateinit var binding: ActivityAllNutritionsBinding
    private lateinit var utilities: Utilities
    private lateinit var plansList: ArrayList<MealPlansDataModel>
    var clientId = ""
    var trainerId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllNutritionsBinding.inflate(layoutInflater)
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
        utilities = Utilities(this@AllNutritionsActivity)
        utilities.setGrayBar(this@AllNutritionsActivity)
        val intent = intent
        clientId = intent.getStringExtra("clientid").toString()
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@AllNutritionsActivity, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        trainerId = obj.id.toString()



        binding.rlAddNutrition.setOnClickListener {
            createNewNutrition()
        }
    }

    override fun onClickArea(position: Int) {
        val model = plansList[position]
        val intent = Intent(this@AllNutritionsActivity, ClientNutritionActivity::class.java)
        val gson = Gson()
        val mySelectMeal = gson.toJson(model)
        intent.putExtra("myplan", mySelectMeal)
        intent.putExtra("clientid", clientId)
        intent.putExtra("from","from")
        startActivity(intent)
    }

    private fun allPlansApi(

    ) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@AllNutritionsActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().viewMealPlan(
                "view", clientId,
                trainerId
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
                                plansList = ArrayList()
                                plansList = signupResponse.data
                                binding.rvAllNutritions.layoutManager =
                                    GridLayoutManager(this@AllNutritionsActivity, 2)
                                binding.rvAllNutritions.adapter = AllNutritionsAdapter(
                                    this@AllNutritionsActivity,
                                    plansList,
                                    this@AllNutritionsActivity
                                )
                            } else {
                                utilities.showFailureToast(
                                    this@AllNutritionsActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@AllNutritionsActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<MealPlanResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@AllNutritionsActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@AllNutritionsActivity)
        }
    }

    private fun addNutritionDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_nutrition)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes = lp
        val cardAddWorkout = dialog.findViewById<CardView>(R.id.cardAddWorkout)
        cardAddWorkout.setOnClickListener {
            createNewNutrition()
            dialog.dismiss()
        }

        dialog.show()
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
        layout_send.setOnClickListener {
            val nutritionName1 = nutritionName.text.toString()
            if (nutritionName1.equals("")) {
                utilities.showFailureToast(
                    this@AllNutritionsActivity,
                    "Please enter nutrition name"
                )
            } else {
                val intent = Intent(this@AllNutritionsActivity, CreateNutrtionActivity::class.java)
                intent.putExtra("nutritionName", nutritionName1)
                intent.putExtra("clientid", clientId)
                startActivity(intent)
                finish()
            }


        }

        dialog.show()
    }
}