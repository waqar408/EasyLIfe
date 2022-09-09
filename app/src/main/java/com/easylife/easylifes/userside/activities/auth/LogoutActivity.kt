package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityLogoutBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.forgotpassword.ForgotPasswordResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogoutBinding
    private lateinit var utilities: Utilities
    var userId = ""
    var apiClient  = ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }

    private fun onClicks() {
        binding.rlCancel.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            logoutApi()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@LogoutActivity)
        utilities.setLightGreenBar(this@LogoutActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (!jsonn.isEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = java.lang.String.valueOf(obj.id)
        }
    }


    private fun logoutApi() {
        if (utilities.isConnectingToInternet(this@LogoutActivity)) {
            val url = apiClient.BASE_URL + "logout/"+userId
            utilities.showProgressDialog(this@LogoutActivity,"Please wait...")
            apiClient.getApiService().logout(url)
                .enqueue(object : Callback<BaseResponse> {

                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        val signupResponse = response.body()
                        utilities.hideProgressDialog()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!.equals(true)) {
                                utilities.clearSharedPref(this@LogoutActivity)
                                    val intent = Intent(
                                        this@LogoutActivity,
                                        GetStartedActivity::class.java
                                    )
                                    startActivity(intent)
                                    finishAffinity()
                                    finish()

                            } else {
                                utilities.showFailureToast(
                                    this@LogoutActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@LogoutActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        // Error logging in
                        utilities.hideProgressDialog()
                        utilities.showFailureToast(this@LogoutActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@LogoutActivity)
        }
    }

}