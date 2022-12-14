package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.easylife.easylifes.databinding.ActivityPhoneNumberBinding
import com.easylife.easylifes.model.verifydata.VerifyDataResponseModel
import com.easylife.easylifes.utils.Utilities
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhoneNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneNumberBinding
    private lateinit var utilities: Utilities
    var email = ""
    var password= ""
    var name = ""
    var phoneCode = ""
    private var phoneNumber = ""
    var experience = ""
    val apiClient = ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()


    }

    private fun onClicks() {

        binding.layoutSend.setOnClickListener {
            phoneCode = binding.countryCodePicker.selectedCountryCode
            phoneNumber = binding.editPhoneNumber.text.toString()
            if (phoneNumber == "")
            {
                utilities.showFailureToast(this@PhoneNumberActivity,"Please Enter Phone Number")
            }else{
                verifyData(email,phoneCode,phoneNumber)
            }

        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@PhoneNumberActivity)
        utilities.setWhiteBars(this@PhoneNumberActivity)
        val intent = intent
        name = intent.getStringExtra("name").toString()
        email = intent.getStringExtra("email").toString()
        password = intent.getStringExtra("password").toString()
        experience = intent.getStringExtra("experience").toString()
    }

    private fun verifyData(email: String, countrycode: String, phoneNumber: String) {
        if (utilities.isConnectingToInternet(this@PhoneNumberActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().verifyData(
                email,countrycode,phoneNumber
            )
                .enqueue(object : Callback<VerifyDataResponseModel> {

                    override fun onResponse(
                        call: Call<VerifyDataResponseModel>,
                        response: Response<VerifyDataResponseModel>
                    ) {
                        val signupResponse = response.body()
                        binding.dotloader.visibility = View.GONE
                        if (response.isSuccessful)
                        {
                            if (signupResponse?.status!!) {
                                val intent = Intent(this@PhoneNumberActivity,OtpVerificationActivity::class.java)
                                intent.putExtra("email",email)
                                intent.putExtra("name",name)
                                intent.putExtra("password",password)
                                intent.putExtra("experience",experience)
                                intent.putExtra("phonecode",phoneCode)
                                intent.putExtra("phonenumber",phoneNumber)
                                startActivity(intent)
                            } else {
                                utilities.showFailureToast(this@PhoneNumberActivity,signupResponse.message)
                            }
                        }else{
                            utilities.showFailureToast(this@PhoneNumberActivity,signupResponse!!.message)
                        }

                    }

                    override fun onFailure(call: Call<VerifyDataResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE

                    }

                })
        } else {
            utilities.showNoInternetToast(this@PhoneNumberActivity)
        }
    }
}