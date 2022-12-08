package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.easylife.easylifes.databinding.ActivityOtpVerificationBinding
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpVerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpVerificationBinding
    private lateinit var utilities: Utilities
    var name = ""
    var email = ""
    var password = ""
    var experience = ""
    private var phoneCode = ""
    private var phoneNumber = ""
    private var otp :String? = null
    private var userType = ""
    var apiClient = ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@OtpVerificationActivity)
        utilities.setWhiteBars(this@OtpVerificationActivity)
        userType = utilities.getString(this@OtpVerificationActivity,"usertype")
        val intent = intent
        name = intent.getStringExtra("name").toString()
        email = intent.getStringExtra("email").toString()
        password = intent.getStringExtra("password").toString()
        experience = intent.getStringExtra("experience").toString()
        phoneCode = intent.getStringExtra("phonecode").toString()
        phoneNumber = intent.getStringExtra("phonenumber").toString()
    }

    private fun onClicks() {
        binding.layoutSend.setOnClickListener {
            otp = binding.pinview.text.toString()
            if (otp.isNullOrEmpty())
            {
                utilities.showFailureToast(this@OtpVerificationActivity,"Please Enter Otp")
            }else{
                SignUpApi(name,email,password,userType)
            }
        }

        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun SignUpApi(name: String, email: String, password: String, userType : String) {
        if (utilities.isConnectingToInternet(this@OtpVerificationActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().signUp(
                userType,name,email,password,experience,phoneCode,phoneNumber
            )
                .enqueue(object : Callback<SignupResponseModel> {

                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        val signupResponse = response.body()
                        binding.dotloader.visibility = View.GONE
                        if (response.isSuccessful)
                        {
                            if (signupResponse?.status!!) {
                                utilities.showSuccessToast(this@OtpVerificationActivity,signupResponse.message)
                                if(signupResponse.data.type == "1")
                                {
                                    val gson = Gson()
                                    val json = gson.toJson(signupResponse.data)
                                    utilities.saveString(this@OtpVerificationActivity, "loginResponse", json)
                                    utilities.saveString(this@OtpVerificationActivity, "loggedin", "loggedin")
                                    Handler(Looper.myLooper()!!).postDelayed({
                                        val intent = Intent(this@OtpVerificationActivity,GenderSelectionActivity::class.java)
                                        startActivity(intent)
                                        finishAffinity()
                                        finish()
                                    },2000)
                                }else{
                                    Handler(Looper.myLooper()!!).postDelayed({
                                        val intent = Intent(this@OtpVerificationActivity,LoginActivity::class.java)
                                        startActivity(intent)
                                        finishAffinity()
                                        finish()
                                    },2000)
                                }

                            } else {
                                utilities.showFailureToast(this@OtpVerificationActivity,signupResponse.message)
                            }
                        }else{
                            utilities.showFailureToast(this@OtpVerificationActivity,signupResponse!!.message)
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                    }

                })
        } else {
            utilities.showNoInternetToast(this@OtpVerificationActivity)
        }
    }


}