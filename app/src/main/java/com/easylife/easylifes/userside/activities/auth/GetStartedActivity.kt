package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.easylife.easylifes.databinding.ActivityGetStartedBinding
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.trainerside.activities.TrainerMainActivity
import com.easylife.easylifes.userside.activities.MainActivity
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetStartedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetStartedBinding
    private lateinit var utilities: Utilities
    val apiClient = ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@GetStartedActivity)
        utilities.setWhiteBars(this@GetStartedActivity)
    }

    private fun onClicks() {
        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this@GetStartedActivity, LoginActivity::class.java)
            utilities.saveString(this@GetStartedActivity, "usertype", "user")
            startActivity(intent)
        }
        binding.btnGetStartedAsTrainer.setOnClickListener {
            val intent = Intent(this@GetStartedActivity, LoginActivity::class.java)
            utilities.saveString(this@GetStartedActivity, "usertype", "trainer")
            startActivity(intent)
        }
        binding.tvGuestLogin.setOnClickListener {
            loginApi()
        }
    }

    private fun loginApi() {
        if (utilities.isConnectingToInternet(this@GetStartedActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "guest-login"
            apiClient.getApiService().guestLogin(url)
                .enqueue(object : Callback<SignupResponseModel> {

                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        val signupResponse = response.body()
                        binding.dotloader.visibility = View.GONE
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                utilities.showSuccessToast(
                                    this@GetStartedActivity,
                                    signupResponse.message
                                )
                                if (signupResponse.message == "You'll be Login after Admin Approval") {
                                    utilities.showSuccessToast(
                                        this@GetStartedActivity,
                                        signupResponse.message
                                    )
                                } else {

                                    if (signupResponse.data.type == "3") {
                                        val gson = Gson()
                                        val json = gson.toJson(signupResponse.data)
                                        utilities.saveString(
                                            this@GetStartedActivity,
                                            "loginResponse",
                                            json
                                        )
                                        utilities.saveString(
                                            this@GetStartedActivity,
                                            "loggedin",
                                            "loggedin"
                                        )
                                        val intent = Intent(
                                            this@GetStartedActivity,
                                            MainActivity::class.java
                                        )
                                        startActivity(intent)
                                        finishAffinity()
                                    } else{
                                        utilities.showFailureToast(this@GetStartedActivity,"You are not allowed to login as guest")
                                    }


                                }

                            } else {
                                utilities.showFailureToast(
                                    this@GetStartedActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@GetStartedActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@GetStartedActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@GetStartedActivity)
        }
    }

}