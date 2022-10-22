package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.databinding.ActivityForgotPasswordBinding
import com.easylife.easylifes.model.forgotpassword.ForgotPasswordResponseModel
import com.easylife.easylifes.utils.Utilities
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassword : AppCompatActivity() {
    private lateinit var binding : ActivityForgotPasswordBinding
    private lateinit var utilities: Utilities
    var apiClient = ApiClient()
    private var emailAddress = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }

    private fun initViews() {
        utilities = Utilities(this@ForgotPassword)
        utilities.setWhiteBars(this@ForgotPassword)
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            emailAddress = binding.editPhoneNumber.text.toString().trim()
            val isValidEmail: Boolean = utilities.isValidEmail(emailAddress)

            if (emailAddress == "")
            {
                utilities.showFailureToast(this@ForgotPassword,"Please Enter Your Registered Email Address To Get Otp Code")
            }else if (!isValidEmail)
            {
                utilities.showFailureToast(this@ForgotPassword,"Please Enter Valid Email Address")
            }else{
                forgotPassword(emailAddress)
            }
        }

    }

    private fun forgotPassword(email : String) {
        if (utilities.isConnectingToInternet(this@ForgotPassword)) {
            val url = apiClient.BASE_URL + "forgot-password/"+email
            utilities.showProgressDialog(this@ForgotPassword,"Please wait...")
            apiClient.getApiService().forgotPassword(url)
                .enqueue(object : Callback<ForgotPasswordResponseModel> {

                    override fun onResponse(
                        call: Call<ForgotPasswordResponseModel>,
                        response: Response<ForgotPasswordResponseModel>
                    ) {
                        val signupResponse = response.body()
                        utilities.hideProgressDialog()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                val email1 = signupResponse.data.email
                                val code = signupResponse.data.code
                                val intent=  Intent(this@ForgotPassword,EmailOtpVerficationActivity::class.java)
                                intent.putExtra("email",email1)
                                intent.putExtra("code",code.toString())
                                startActivity(intent)

                            } else {
                                utilities.showFailureToast(
                                    this@ForgotPassword,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@ForgotPassword,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<ForgotPasswordResponseModel>, t: Throwable) {
                        // Error logging in
                        utilities.hideProgressDialog()
                        utilities.showFailureToast(this@ForgotPassword, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@ForgotPassword)
        }
    }
}