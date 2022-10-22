package com.easylife.easylifes.userside.activities.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.easylife.easylifes.databinding.ActivitySettingsBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.userside.activities.auth.ChangePasswordActivity
import com.easylife.easylifes.userside.activities.auth.LogoutActivity
import com.easylife.easylifes.userside.activities.choosepackage.PaymentActivity
import com.easylife.easylifes.userside.activities.profile.EditProfileActivity
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var utilities: Utilities
    var adminNotification = ""
    var appNotification = ""
    var userId = ""
    val apiClient = ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.lnChangePassword.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, ChangePasswordActivity::class.java))
        }
        binding.lnSignout.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, LogoutActivity::class.java))
        }
        binding.lnEditAccount.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, EditProfileActivity::class.java))
        }
        binding.lnPayment.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, PaymentActivity::class.java))
        }
    }

    private fun initViews() {
        utilities = Utilities(this@SettingsActivity)
        utilities.setGrayBar(this@SettingsActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@SettingsActivity, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        userId = obj.id.toString()
        appNotification = obj.allow_notifications
        adminNotification = obj.allow_admin_notifications

        if (appNotification == "0")
        {
            binding.switchAppNotification.isChecked = false
        }else{
            binding.switchAppNotification.isChecked = true
        }

        if (adminNotification == "1")
        {
            binding.switchAdminNotification.isChecked = true
        }else{
            binding.switchAdminNotification.isChecked = false
        }

        binding.switchAppNotification.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                updateAppNotification("1")
            }else{
                updateAppNotification("0")
            }
        })
        binding.switchAdminNotification.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                updateAdminNotification("1")
            }else{
                updateAdminNotification("0")
            }
        })
    }


    private fun updateAppNotification(
        notification: String) {
        if (utilities.isConnectingToInternet(this@SettingsActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().updateAppNotification(
                userId,notification)
                .enqueue(object : Callback<SignupResponseModel> {

                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        val signupResponse = response.body()
                        binding.dotloader.visibility = View.GONE
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                utilities.showSuccessToast(this@SettingsActivity, signupResponse.message)
                                val gson = Gson()
                                val json = gson.toJson(signupResponse.data)
                                utilities.saveString(this@SettingsActivity, "loginResponse", json)
                                val appNoti = signupResponse.data.allow_notifications
                                val adminNoti  = signupResponse.data.allow_admin_notifications
                                if (appNoti == "0")
                                {
                                    binding.switchAppNotification.isChecked = false
                                }else{
                                    binding.switchAppNotification.isChecked = true
                                }

                                if (adminNoti == "1")
                                {
                                    binding.switchAdminNotification.isChecked = true
                                }else{
                                    binding.switchAdminNotification.isChecked = false
                                }
                            } else {
                                utilities.showFailureToast(
                                    this@SettingsActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@SettingsActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@SettingsActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@SettingsActivity)
        }
    }

    private fun updateAdminNotification(
        notification: String) {
        if (utilities.isConnectingToInternet(this@SettingsActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().updateAdminNotification(
                userId,notification)
                .enqueue(object : Callback<SignupResponseModel> {

                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        val signupResponse = response.body()
                        binding.dotloader.visibility = View.GONE
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                utilities.showSuccessToast(this@SettingsActivity, signupResponse.message)
                                val gson = Gson()
                                val json = gson.toJson(signupResponse.data)
                                utilities.saveString(this@SettingsActivity, "loginResponse", json)
                                val appNoti = signupResponse.data.allow_notifications
                                val adminNoti  = signupResponse.data.allow_admin_notifications
                                if (appNoti == "0")
                                {
                                    binding.switchAppNotification.isChecked = false
                                }else{
                                    binding.switchAppNotification.isChecked = true
                                }

                                if (adminNoti == "1")
                                {
                                    binding.switchAdminNotification.isChecked = true
                                }else{
                                    binding.switchAdminNotification.isChecked = false
                                }
                            } else {
                                utilities.showFailureToast(
                                    this@SettingsActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@SettingsActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@SettingsActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@SettingsActivity)
        }
    }
}