package com.easylife.easylifes.userside.activities.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.Support.PrivacyPolicyActivity
import com.easylife.easylifes.userside.activities.Support.SupportActivity
import com.easylife.easylifes.userside.activities.auth.LogoutActivity
import com.easylife.easylifes.databinding.ActivityProfileSettingBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.utils.Utilities
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileSettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileSettingBinding
    private lateinit var utilities: Utilities
    var profileImage = ""
    var userId = ""
    var userName = ""
    var location = ""
    var countrCode = ""
    var imagefile: File? = null
    lateinit var imageFileToUpload: MultipartBody.Part
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        statusbarColor()
        onCLicks()
    }

    private fun onCLicks() {
        binding.changeProfile.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .start()
        }
        binding.layoutBackArrow.setOnClickListener { 
            finish()
        }
        binding.rlAccountSettings.setOnClickListener {
            binding.rlAccountSettings.setBackgroundResource(R.drawable.bg_haiti_rounded_20dp)
            binding.rlAccountSettings2.setBackgroundResource(R.drawable.bg_haiti_rounded_8dp)
            binding.imgAccountSettings.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.white))
            binding.tvAccountSettings.setTextColor(resources.getColor(R.color.white))
            binding.imgAccountSettingsArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.white))
            binding.rlNotifcation.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlNotifcation2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgNotifcation.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvNotifcation.setTextColor(resources.getColor(R.color.haiti))
            binding.imgNotifcationArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlSupport.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlSupport2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgSupport.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvSupport.setTextColor(resources.getColor(R.color.haiti))
            binding.imgSupportArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlPrivacyPolicy.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlPrivacyPolicy2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgPrivacyPolicy.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvPrivacyPolicy.setTextColor(resources.getColor(R.color.haiti))
            binding.imgPrivacyPolicyArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlLogout.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlLogout2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgLogout.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvLogout.setTextColor(resources.getColor(R.color.haiti))
            binding.imgLogoutArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))

            startActivity(Intent(this@ProfileSettingActivity,EditProfileActivity::class.java))
        }
        binding.rlNotifcation.setOnClickListener {
            binding.rlNotifcation.setBackgroundResource(R.drawable.bg_haiti_rounded_20dp)
            binding.rlNotifcation2.setBackgroundResource(R.drawable.bg_haiti_rounded_8dp)
            binding.imgNotifcation.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.white))
            binding.tvNotifcation.setTextColor(resources.getColor(R.color.white))
            binding.imgNotifcationArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.white))
            binding.rlAccountSettings.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlAccountSettings2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgAccountSettings.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvAccountSettings.setTextColor(resources.getColor(R.color.haiti))
            binding.imgAccountSettingsArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlSupport.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlSupport2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgSupport.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvSupport.setTextColor(resources.getColor(R.color.haiti))
            binding.imgSupportArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlPrivacyPolicy.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlPrivacyPolicy2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgPrivacyPolicy.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvPrivacyPolicy.setTextColor(resources.getColor(R.color.haiti))
            binding.imgPrivacyPolicyArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlLogout.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlLogout2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgLogout.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvLogout.setTextColor(resources.getColor(R.color.haiti))
            binding.imgLogoutArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
        }
        binding.rlSupport.setOnClickListener {
            binding.rlAccountSettings.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlAccountSettings2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgAccountSettings.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvAccountSettings.setTextColor(resources.getColor(R.color.haiti))
            binding.imgAccountSettingsArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlSupport.setBackgroundResource(R.drawable.bg_haiti_rounded_20dp)
            binding.rlSupport2.setBackgroundResource(R.drawable.bg_haiti_rounded_8dp)
            binding.imgSupport.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.white))
            binding.tvSupport.setTextColor(resources.getColor(R.color.white))
            binding.imgSupportArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.white))
            binding.rlNotifcation.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlNotifcation2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgNotifcation.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvNotifcation.setTextColor(resources.getColor(R.color.haiti))
            binding.imgNotifcationArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlPrivacyPolicy.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlPrivacyPolicy2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgPrivacyPolicy.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvPrivacyPolicy.setTextColor(resources.getColor(R.color.haiti))
            binding.imgPrivacyPolicyArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlLogout.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlLogout2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgLogout.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvLogout.setTextColor(resources.getColor(R.color.haiti))
            binding.imgLogoutArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            startActivity(Intent(this@ProfileSettingActivity,SupportActivity::class.java))
        }
        binding.rlPrivacyPolicy.setOnClickListener {
            binding.rlAccountSettings.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlAccountSettings2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgAccountSettings.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvAccountSettings.setTextColor(resources.getColor(R.color.haiti))
            binding.imgAccountSettingsArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlNotifcation.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlNotifcation2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgNotifcation.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvNotifcation.setTextColor(resources.getColor(R.color.haiti))
            binding.imgNotifcationArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlPrivacyPolicy.setBackgroundResource(R.drawable.bg_haiti_rounded_20dp)
            binding.rlPrivacyPolicy2.setBackgroundResource(R.drawable.bg_haiti_rounded_8dp)
            binding.imgPrivacyPolicy.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.white))
            binding.tvPrivacyPolicy.setTextColor(resources.getColor(R.color.white))
            binding.imgPrivacyPolicyArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.white))
            binding.rlSupport.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlSupport2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgSupport.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvSupport.setTextColor(resources.getColor(R.color.haiti))
            binding.imgSupportArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlLogout.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlLogout2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgLogout.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvLogout.setTextColor(resources.getColor(R.color.haiti))
            binding.imgLogoutArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            startActivity(Intent(this@ProfileSettingActivity,PrivacyPolicyActivity::class.java))
        }
        binding.rlLogout.setOnClickListener {
            binding.rlAccountSettings.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlAccountSettings2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgAccountSettings.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvAccountSettings.setTextColor(resources.getColor(R.color.haiti))
            binding.imgAccountSettingsArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlNotifcation.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlNotifcation2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgNotifcation.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvNotifcation.setTextColor(resources.getColor(R.color.haiti))
            binding.imgNotifcationArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlSupport.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlSupport2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgSupport.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvSupport.setTextColor(resources.getColor(R.color.haiti))
            binding.imgSupportArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.rlLogout.setBackgroundResource(R.drawable.bg_haiti_rounded_20dp)
            binding.rlLogout2.setBackgroundResource(R.drawable.bg_haiti_rounded_8dp)
            binding.imgLogout.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.white))
            binding.tvLogout.setTextColor(resources.getColor(R.color.white))
            binding.imgLogoutArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.white))
            binding.rlPrivacyPolicy.setBackgroundResource(R.drawable.bg_white_rounded_20dp)
            binding.rlPrivacyPolicy2.setBackgroundResource(R.drawable.bg_white_rounded_8dp)
            binding.imgPrivacyPolicy.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            binding.tvPrivacyPolicy.setTextColor(resources.getColor(R.color.haiti))
            binding.imgPrivacyPolicyArrow.setColorFilter(ContextCompat.getColor(this@ProfileSettingActivity,R.color.haiti))
            startActivity(Intent(this@ProfileSettingActivity, LogoutActivity::class.java))
        }
    }

    private fun initView() {
        utilities = Utilities(this@ProfileSettingActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (!jsonn.isEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileImage = obj.profile_image
            userId = obj.id.toString()
            userName = obj.username
            location = obj.location
            Glide.with(this@ProfileSettingActivity).load(profileImage).into(binding.profileImage)
            binding.userName.text = Editable.Factory.getInstance().newEditable(userName)
            binding.location.text = Editable.Factory.getInstance().newEditable(location)

        }
    }
    private fun statusbarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow()
                .setStatusBarColor(getColor(R.color.haiti))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().getDecorView().getWindowInsetsController()!!
                .setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        }
    }

    //imagepicker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            binding.profileImage.setImageURI(uri)
            val photoFile = File(uri.path!!)
            imagefile = photoFile


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            utilities.showFailureToast(this@ProfileSettingActivity,ImagePicker.getError(data))

        } else {
            //do nothing here

        }
    }

    fun withProfileImageApi() {
        val apiClient: ApiClient = ApiClient()
        val userId: RequestBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val countryCode: RequestBody = countrCode.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBody: RequestBody = RequestBody.create("*/*".toMediaTypeOrNull(), imagefile!!)
        imageFileToUpload = MultipartBody.Part.createFormData("profile_image", imagefile!!.name, requestBody)
        utilities.showProgressDialog(this@ProfileSettingActivity,"Please wait...")
        apiClient.getApiService().editProfile(
            userId,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            countryCode,
            imageFileToUpload
        )
            .enqueue(object : Callback<SignupResponseModel> {
                override fun onResponse(
                    call: Call<SignupResponseModel>,
                    response: Response<SignupResponseModel>
                ) {
                    utilities.hideProgressDialog()
                    if (response.isSuccessful) {
                        val status: Boolean = response.body()!!.status
                        if (status.equals(true)) {
                            val signupResponse = response.body()
                            val message: String = response.body()!!.message
                            utilities.showSuccessToast(this@ProfileSettingActivity,message)
                            val gson = Gson()
                            val json = gson.toJson(signupResponse!!.data)
                            utilities.saveString(this@ProfileSettingActivity, "loginResponse", json)
                            profileImage = signupResponse.data.profile_image
                            Glide.with(this@ProfileSettingActivity).load(profileImage).into(binding.profileImage)


                        } else {
                            val message: String = response.body()!!.message

                            utilities.showFailureToast(this@ProfileSettingActivity,message.toString())

                        }
                    } else {
                        val message: String = response.body()!!.message
                        utilities.showFailureToast(this@ProfileSettingActivity,message.toString())

                    }
                }

                override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                    utilities.hideProgressDialog()
                    utilities.showFailureToast(this@ProfileSettingActivity,t.message.toString())

                }
            })


    }


}