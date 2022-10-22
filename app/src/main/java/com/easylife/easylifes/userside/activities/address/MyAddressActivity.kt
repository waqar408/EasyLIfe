package com.easylife.easylifes.userside.activities.address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityMyAddressBinding
import com.easylife.easylifes.model.address.AddressResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyAddressBinding
    private lateinit var utilities: Utilities
    var userId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()

    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            val name = binding.edName.text.toString()
            val edPhone = binding.edPhone.text.toString()
            val email = binding.edEmail.text.toString()
            val edAddress = binding.edAddress.text.toString()
            addAddress(name, edPhone, email, edAddress)
        }
    }

    private fun initViews() {
        utilities = Utilities(this@MyAddressActivity)
        utilities.setGrayBar(this@MyAddressActivity)
        getMyAddress()

    }

    private fun getMyAddress() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@MyAddressActivity)) {
            val gsonn = Gson()
            val jsonn: String = utilities.getString(this@MyAddressActivity, "loginResponse")
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = java.lang.String.valueOf(obj.id)

            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().userAddress("view",
                userId, "", "", "", "")
                .enqueue(object : Callback<AddressResponseModel> {

                    override fun onResponse(
                        call: Call<AddressResponseModel>,
                        response: Response<AddressResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (signupResponse!!.status) {

                            binding.card.visibility = View.VISIBLE
                            val name = signupResponse.data.address_title
                            val email = signupResponse.data.email
                            val phone = signupResponse.data.phone
                            val address = signupResponse.data.address
                            binding.edName.text = Editable.Factory.getInstance().newEditable(name)
                            binding.edPhone.text = Editable.Factory.getInstance().newEditable(phone)
                            binding.edEmail.text = Editable.Factory.getInstance().newEditable(email)
                            binding.edAddress.text = Editable.Factory.getInstance().newEditable(address)
                        } else {
                            utilities.showFailureToast(
                                this@MyAddressActivity,
                                signupResponse.message
                            )


                        }


                    }

                    override fun onFailure(call: Call<AddressResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@MyAddressActivity, t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(
                this@MyAddressActivity,
                resources.getString(R.string.checkinternet)
            )

        }

    }

    private fun addAddress(
        name: String,
        edPhone: String,
        email: String,
        edAddress: String
    ) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@MyAddressActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().userAddress(
                "update",
                userId, name, edPhone, email, edAddress
            )
                .enqueue(object : Callback<AddressResponseModel> {

                    override fun onResponse(
                        call: Call<AddressResponseModel>,
                        response: Response<AddressResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                utilities.showSuccessToast(
                                    this@MyAddressActivity,
                                    signupResponse.message
                                )

                            } else {
                                utilities.showFailureToast(
                                    this@MyAddressActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@MyAddressActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<AddressResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@MyAddressActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@MyAddressActivity)
        }
    }

}