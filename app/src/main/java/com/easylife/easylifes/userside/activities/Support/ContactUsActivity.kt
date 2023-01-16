package com.easylife.easylifes.userside.activities.Support

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easylife.easylifes.R
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import com.easylife.easylifes.databinding.ActivityContactUsBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactUsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactUsBinding
    lateinit var utilities: Utilities
    var apiClient = ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        utilities = Utilities(this@ContactUsActivity)
        utilities.setWhiteBars(this@ContactUsActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@ContactUsActivity, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        val name = obj.name
        val phone = obj.phone
        val address = obj.address
        binding.edName.text  =Editable.Factory.getInstance().newEditable(name)
        binding.edPhoneNumber.text = Editable.Factory.getInstance().newEditable(phone)
        binding.edAddress.text = Editable.Factory.getInstance().newEditable(address)

        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            val name = binding.edName.text.toString()
            val phone = binding.edPhoneNumber.text.toString()
            val edAddress = binding.edAddress.text.toString()
            val query = binding.edQuery.text.toString()
            if (name == "") {
                utilities.showFailureToast(this@ContactUsActivity, "Please enter your name")
            } else if (phone == "") {
                utilities.showFailureToast(this@ContactUsActivity, "Please enter your phone")
            } else if (edAddress == "") {
                utilities.showFailureToast(this@ContactUsActivity, "Please enter your email")
            } else if (query == "") {
                utilities.showFailureToast(this@ContactUsActivity, "Please enter your query")
            } else {
                contactUsApi(name, phone, edAddress, query)
            }
        }
    }

    private fun contactUsApi(name: String, phone: String, edAddress: String, query: String) {
        if (utilities.isConnectingToInternet(this@ContactUsActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().contactus(
                name, phone, edAddress, query
            )
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
                                    this@ContactUsActivity,
                                    "We will get back to you soon!"
                                )
                                Handler(Looper.myLooper()!!).postDelayed({
                                    finish()
                                }, 500)
                            } else {
                                utilities.showFailureToast(
                                    this@ContactUsActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@ContactUsActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@ContactUsActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@ContactUsActivity)
        }
    }
}