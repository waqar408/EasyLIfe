package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.CustomizeInterestAdapter
import com.easylife.easylifes.databinding.ActivityCustomizeInterestBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomizeInterestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomizeInterestBinding
    private lateinit var adapter1: CustomizeInterestAdapter
    private lateinit var customizeInterestList: ArrayList<JobsDataModel>
    lateinit var nameList: Array<String>
    private lateinit var utilities: Utilities

    var stringBuilder: StringBuilder = StringBuilder()
    var stringBuilderInt: StringBuilder = StringBuilder()
    val apiClient = ApiClient()
    var gender = ""
    var age = ""
    var weight = ""
    var weightUnit = ""
    var height = ""
    var heightUnit = ""
    var interest = ""
    var achieve = ""
    var currentFitnessLevel = ""
    var customizeInterest = ""
    var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizeInterestBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()

    }

    private fun onClicks() {
        /* binding.layoutSend.setOnClickListener {
             startActivity(Intent(this@CustomizeInterestActivity,OnBoardingActivity::class.java))
         }*/
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@CustomizeInterestActivity)
        utilities.setWhiteBars(this@CustomizeInterestActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (!jsonn.isEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = java.lang.String.valueOf(obj.id)
        }

        gender = intent.getStringExtra("gender").toString()
        age = intent.getStringExtra("age").toString()
        weight = intent.getStringExtra("weight").toString()
        weightUnit = intent.getStringExtra("wUnit").toString()
        heightUnit = intent.getStringExtra("hunit").toString()
        height = intent.getStringExtra("height").toString()
        interest = intent.getStringExtra("interest").toString()
        achieve = intent.getStringExtra("goal").toString()
        currentFitnessLevel = intent.getStringExtra("currentfitness").toString()
        customizeInterestList = ArrayList()
        customizeInterestList.add(
            JobsDataModel(
                R.drawable.nutrition,
                resources.getString(R.string.nutrition)
            )
        )
        customizeInterestList.add(
            JobsDataModel(
                R.drawable.organic,
                resources.getString(R.string.organic)
            )
        )
        customizeInterestList.add(
            JobsDataModel(
                R.drawable.meditation,
                resources.getString(R.string.meditation)
            )
        )
        customizeInterestList.add(
            JobsDataModel(
                R.drawable.sports,
                resources.getString(R.string.sports)
            )
        )
        customizeInterestList.add(
            JobsDataModel(
                R.drawable.smoke,
                resources.getString(R.string.smokefree)
            )
        )
        customizeInterestList.add(
            JobsDataModel(
                R.drawable.sleep,
                resources.getString(R.string.sleep)
            )
        )
        customizeInterestList.add(
            JobsDataModel(
                R.drawable.health,
                resources.getString(R.string.health)
            )
        )
        customizeInterestList.add(
            JobsDataModel(
                R.drawable.running,
                resources.getString(R.string.running)
            )
        )
        customizeInterestList.add(
            JobsDataModel(
                R.drawable.vegan,
                resources.getString(R.string.vegan)
            )
        )
        binding.rvCustomize.layoutManager = GridLayoutManager(this@CustomizeInterestActivity, 3)
        binding.rvCustomize.adapter =
            CustomizeInterestAdapter(this@CustomizeInterestActivity, customizeInterestList)

        binding.layoutSend.setOnClickListener {
            adapter1 =
                CustomizeInterestAdapter(this@CustomizeInterestActivity, customizeInterestList)
            stringBuilder = java.lang.StringBuilder()
            if (adapter1.selected.size > 0) {
                for (i in 0 until adapter1.selected.size) {
                    stringBuilder.append(adapter1.selected.get(i).name)
                    stringBuilder.append(",")
                }


                // showToast(stringBuilder.toString().trim { it <= ' ' })
                if (!stringBuilder.toString().equals("")) {
                    stringBuilder = stringBuilder.deleteCharAt(stringBuilder.length - 1)
                }
                customizeInterest = stringBuilder.toString()
                completeProfileDataApi(
                    userId, gender, age, weight,
                    weightUnit, height, heightUnit, interest,
                    achieve, currentFitnessLevel, customizeInterest
                )
            } else {
                utilities.showFailureToast(this@CustomizeInterestActivity, "List is empty")
            }
            Log.d("codehere", stringBuilder.toString())

        }

    }

    private fun completeProfileDataApi(
        userId: String,
        gender: String,
        age: String,
        weight: String,
        weightUnit: String,
        height: String,
        heightUnit: String,
        interest: String,
        achieve: String,
        currentFitnessLevel: String,
        customizeInterest: String
    ) {
        if (utilities.isConnectingToInternet(this@CustomizeInterestActivity)) {
            utilities.showProgressDialog(this@CustomizeInterestActivity, "Please wait...")
            apiClient.getApiService().updateProfile(
                userId, "",
                "", "",
                "", gender, age, weight,
                weightUnit, height, heightUnit,
                interest, achieve, currentFitnessLevel,
                "", "", "",
                "", "", "", "", "", customizeInterest
            )
                .enqueue(object : Callback<SignupResponseModel> {

                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        val signupResponse = response.body()
                        utilities.hideProgressDialog()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!.equals(true)) {
                                utilities.showSuccessToast(
                                    this@CustomizeInterestActivity,
                                    signupResponse.message
                                )
                                val gson = Gson()
                                val json = gson.toJson(signupResponse.data)
                                utilities.saveString(
                                    this@CustomizeInterestActivity,
                                    "loginResponse",
                                    json
                                )
                                Handler(Looper.myLooper()!!).postDelayed({
                                    val intent = Intent(
                                        this@CustomizeInterestActivity,
                                        OnBoardingActivity::class.java
                                    )
                                    startActivity(intent)
                                    finishAffinity()
                                    finish()
                                }, 2000)
                            } else {
                                utilities.showFailureToast(
                                    this@CustomizeInterestActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@CustomizeInterestActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        utilities.hideProgressDialog()
                        utilities.showFailureToast(this@CustomizeInterestActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@CustomizeInterestActivity)
        }
    }


}