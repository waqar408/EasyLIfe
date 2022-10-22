package com.easylife.easylifes.userside.activities.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityChangePasswordBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChangePasswordBinding
    private lateinit var utilities : Utilities
    private var passwordVisibile = false
    private var passwordVisibile2 = false
    private var passwordVisibile3= false
    var password = ""
    private var confirmPassword = ""
    private var oldPassword = ""
    var userId = ""
    val apiClient =  ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()

    }
    private fun initViews() {
        utilities = Utilities(this@ChangePasswordActivity)
        utilities.setWhiteBars(this@ChangePasswordActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = java.lang.String.valueOf(obj.id)
        }
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
        binding.layoutShowHide3.setOnClickListener {
            passwordVisibile3 = !passwordVisibile3
            showPassword3(passwordVisibile3)
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            oldPassword= binding.editOldPassword.text.toString()
            password  =binding.editPassword.text.toString()
            confirmPassword = binding.editConfirmpassword.text.toString()
            if (oldPassword == "")
            {
                utilities.showFailureToast(this@ChangePasswordActivity,"Please Enter Old Password")

            }else if (password == "")
            {
                utilities.showFailureToast(this@ChangePasswordActivity,"Please Enter Password")
            }else if (password.length<6)
            {
                utilities.showFailureToast(this@ChangePasswordActivity,"Password Length Must Be Greater Than 6")

            }else if (confirmPassword == "")
            {
                utilities.showFailureToast(this@ChangePasswordActivity,"Please Confirm Your Password")

            }else if (confirmPassword != password)
            {
                utilities.showFailureToast(this@ChangePasswordActivity,"Password Does Not Match")
            }else{
                resetPasswordApi(oldPassword,password)
            }
        }
    }

    private fun resetPasswordApi(oldPassword: String, password: String) {
        if (utilities.isConnectingToInternet(this@ChangePasswordActivity)) {
            utilities.showProgressDialog(this@ChangePasswordActivity,"Reseting Passowrd...")
            apiClient.getApiService().changePassword(
                userId,oldPassword,password)
                .enqueue(object : Callback<SignupResponseModel> {

                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        val signupResponse = response.body()
                        utilities.hideProgressDialog()
                        if (response.isSuccessful)
                        {
                            if (signupResponse?.status!!) {
                                utilities.showSuccessToast(this@ChangePasswordActivity,signupResponse.message)
                                Handler(Looper.myLooper()!!).postDelayed({
                                    finish()
                                },1500)
                            } else {
                                utilities.showFailureToast(this@ChangePasswordActivity,signupResponse.message)
                            }
                        }else{
                            utilities.showFailureToast(this@ChangePasswordActivity,signupResponse!!.message)
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        utilities.hideProgressDialog()
                        utilities.showFailureToast(this@ChangePasswordActivity,t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@ChangePasswordActivity)
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
    private fun showPassword3(isShow :Boolean){
        if (isShow){
            binding.editOldPassword.transformationMethod= HideReturnsTransformationMethod.getInstance()
            binding.eyesHidePassword3.setImageResource(R.drawable.visible_icon)
        }else{
            binding.editOldPassword.transformationMethod= PasswordTransformationMethod.getInstance()
            binding.eyesHidePassword3.setImageResource(R.drawable.ic_eye_icn)
        }
//        binding.editConfirmpassword.setSelection(binding.editPassword.text.toString())
    }
}