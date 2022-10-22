package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivitySignUpBinding
import com.easylife.easylifes.model.verifydata.VerifyDataResponseModel
import com.easylife.easylifes.utils.Utilities
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var utilities: Utilities
    private lateinit var binding: ActivitySignUpBinding
    private var name = ""
    private var email = ""
    private var password = ""
    private var passwordVisibile = false
    private var experience = ""
    private var userType = ""
    private var phoneCode = ""
    private var phoneNumber = ""
    val apiClient = ApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@SignUpActivity)
        utilities.setWhiteBars(this@SignUpActivity)
        userType = utilities.getString(this@SignUpActivity,"usertype")
        if (userType == "user")
        {
            binding.layoutExperience.visibility = View.GONE
        }else{

            binding.layoutExperience.visibility = View.VISIBLE
        }
    }

    private fun onClicks() {
        binding.layoutShowHide.setOnClickListener {
            passwordVisibile=!passwordVisibile
            showPassword(passwordVisibile)
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSignUp.setOnClickListener {
            name = binding.editFullName.text.toString()
            email = binding.editEmail.text.toString()
            password = binding.editPassword.text.toString()
            experience = binding.editExperience.text.toString()
            phoneCode = binding.countryCodePicker.selectedCountryCodeWithPlus
            phoneNumber = binding.editPhoneNumber.text.toString()
            val isValidEmail: Boolean = utilities.isValidEmail(email)

            if (name == "")
            {
                utilities.showFailureToast(this@SignUpActivity,"Required!","Please Enter Name")
            }else if (email == "")
            {
                utilities.showFailureToast(this@SignUpActivity,"Required!","Please Enter Email")
            }else if (!isValidEmail)
            {
                utilities.showFailureToast(this@SignUpActivity,"Required!","Please Enter Valid Email")
            }else if (phoneNumber == "")
            {
                utilities.showFailureToast(this@SignUpActivity,"Required!","Please Enter Phone Number")
            }else if (password == "")
            {
                utilities.showFailureToast(this@SignUpActivity,"Required!","Please Enter Password")
            }else if (password.length<6)
            {
                utilities.showFailureToast(this@SignUpActivity,"Required!","Please 6 Digit Passowrd")
            }else if (userType == "trainer")
            {
                if (experience == "")
                {
                    utilities.showFailureToast(this@SignUpActivity,"Required!","Please enter experience")
                }else{
                    verifyData(email,phoneCode,phoneNumber)
                }
            }else
            {
                verifyData(email,phoneCode,phoneNumber)
            }
        }
        binding.layoutAlreadyAccount.setOnClickListener {
            startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
            finishAffinity()
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
        binding.editPassword.setSelection(binding.editPassword.text.toString().length)
    }

    private fun verifyData(email: String, countrycode: String, phoneNumber: String) {
        if (utilities.isConnectingToInternet(this@SignUpActivity)) {
            utilities.showProgressDialog(this@SignUpActivity,"Please wait...")
            apiClient.getApiService().verifyData(
                email,countrycode,phoneNumber
            )
                .enqueue(object : Callback<VerifyDataResponseModel> {

                    override fun onResponse(
                        call: Call<VerifyDataResponseModel>,
                        response: Response<VerifyDataResponseModel>
                    ) {
                        val signupResponse = response.body()
                        utilities.hideProgressDialog()
                        if (response.isSuccessful)
                        {
                            if (signupResponse?.status!!) {
                                if(userType == "trainer")
                                {
                                    val intent = Intent(this@SignUpActivity,OtpVerificationActivity::class.java)
                                    intent.putExtra("name",name)
                                    intent.putExtra("email",email)
                                    intent.putExtra("password",password)
                                    intent.putExtra("experience",experience)
                                    intent.putExtra("phonecode",phoneCode)
                                    intent.putExtra("phonenumber",phoneNumber)
                                    startActivity(intent)
                                }else{
                                    val intent = Intent(this@SignUpActivity,OtpVerificationActivity::class.java)
                                    intent.putExtra("name",name)
                                    intent.putExtra("email",email)
                                    intent.putExtra("password",password)
                                    intent.putExtra("experience","")
                                    intent.putExtra("phonecode",phoneCode)
                                    intent.putExtra("phonenumber",phoneNumber)
                                    startActivity(intent)
                                }

                            } else {
                                utilities.showFailureToast(this@SignUpActivity,signupResponse.message)
                            }
                        }else{
                            utilities.showFailureToast(this@SignUpActivity,signupResponse!!.message)
                        }

                    }

                    override fun onFailure(call: Call<VerifyDataResponseModel>, t: Throwable) {
                        // Error logging in
                        utilities.hideProgressDialog()
                        utilities.showFailureToast(this@SignUpActivity,t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@SignUpActivity)
        }
    }


}