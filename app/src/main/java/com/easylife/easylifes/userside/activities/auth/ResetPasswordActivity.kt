package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityResetPasswordBinding
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.utils.Utilities
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResetPasswordBinding
    private lateinit var utilities : Utilities
    private var passwordVisibile = false
    private var passwordVisibile2 = false
    private var emailAddress = ""
    var password = ""
    private var confirmPassword = ""
    val apiClient =  ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }
    private fun initViews() {
        utilities = Utilities(this@ResetPasswordActivity)
        utilities.setWhiteBars(this@ResetPasswordActivity)
        val intent = intent
        emailAddress = intent.getStringExtra("email").toString()
    }

    private fun onClicks() {
        binding.layoutShowHide.setOnClickListener {
            passwordVisibile = !passwordVisibile
            showPassword(passwordVisibile)
        }
        binding.layoutShowHide2.setOnClickListener {
            passwordVisibile2 = !passwordVisibile2
            showPassword2(passwordVisibile2)
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            password  =binding.editPassword.text.toString()
            confirmPassword = binding.editConfirmpassword.text.toString()
            if (password == "")
            {
                utilities.showFailureToast(this@ResetPasswordActivity,"Please Enter Password")
            }else if (password.length<6)
            {
                utilities.showFailureToast(this@ResetPasswordActivity,"Password Length Must Be Greater Than 6")

            }else if (confirmPassword == "")
            {
                utilities.showFailureToast(this@ResetPasswordActivity,"Please Confirm Your Password")

            }else if (confirmPassword != password)
            {
                utilities.showFailureToast(this@ResetPasswordActivity,"Password Does Not Match")
            }else{
                resetPasswordApi(emailAddress,password,confirmPassword)
            }
        }
    }

    private fun resetPasswordApi(emailAddress: String, password: String, confirmPassword: String) {
        if (utilities.isConnectingToInternet(this@ResetPasswordActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().resetPassword(
                emailAddress,password,confirmPassword)
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
                                utilities.showSuccessToast(this@ResetPasswordActivity,signupResponse.message)
                                Handler(Looper.myLooper()!!).postDelayed({
                                    val intent = Intent(this@ResetPasswordActivity,PasswordSuccessActivity::class.java)
                                    startActivity(intent)
                                    finishAffinity()
                                    finish()
                                },2000)
                            } else {
                                utilities.showFailureToast(this@ResetPasswordActivity,signupResponse.message)
                            }
                        }else{
                            utilities.showFailureToast(this@ResetPasswordActivity,signupResponse!!.message)
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE

                    }

                })
        } else {
            utilities.showNoInternetToast(this@ResetPasswordActivity)
        }
    }

    private fun showPassword(isShow :Boolean){
        if (isShow){
            binding.editPassword.transformationMethod= HideReturnsTransformationMethod.getInstance()
            binding.eyesHidePassword.setImageResource(R.drawable.visible_icon)
        }else{
            binding.editPassword.transformationMethod= PasswordTransformationMethod.getInstance()
            binding.eyesHidePassword.setImageResource(R.drawable.ic_eye_icn)
        }
//        binding.editPassword.setSelection(binding.editPassword.text.toString().length)
    }
    private fun showPassword2(isShow :Boolean){
        if (isShow){
            binding.editConfirmpassword.transformationMethod= HideReturnsTransformationMethod.getInstance()
            binding.eyesHidePassword2.setImageResource(R.drawable.visible_icon)
        }else{
            binding.editConfirmpassword.transformationMethod= PasswordTransformationMethod.getInstance()
            binding.eyesHidePassword2.setImageResource(R.drawable.ic_eye_icn)
        }
//        binding.editConfirmpassword.setSelection(binding.editPassword.text.toString())
    }
}