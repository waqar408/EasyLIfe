package com.easylife.easylifes.userside.activities.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.WindowInsetsController
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityEditProfileBinding
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

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfileBinding
    lateinit var selected_img_bitmap: Bitmap
    private lateinit var utilities: Utilities
    var profileImage = ""
    var username =""
    var name = ""
    var location = ""
    var address  = ""
    var userId = ""
    var phoneCode = ""
    var phoneNumber = ""
    var countryCode = ""
    private var imageUploaded: Boolean = false
    val apiClient=  ApiClient()
    var imagefile: File? = null
    lateinit var imageFileToUpload: MultipartBody.Part
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
        statusbarColor()
    }

    private fun initViews() {
        utilities = Utilities(this@EditProfileActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (!jsonn.isEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileImage = obj.profile_image
            userId = obj.id.toString()
            username = obj.username
            name = obj.name
            location = obj.location
            address = obj.address
            phoneCode = obj.country_code
            phoneNumber= obj.phone
            Glide.with(this@EditProfileActivity).load(profileImage).into(binding.profileImage)
            binding.edName.text = Editable.Factory.getInstance().newEditable(name)
            binding.edUserName.text = Editable.Factory.getInstance().newEditable(username)
            binding.tvPhoneNumber.text = phoneCode+" "+phoneNumber
            binding.edLocation.text = Editable.Factory.getInstance().newEditable(location)
            binding.edAddress.text = Editable.Factory.getInstance().newEditable(address)
            binding.userName.text = username
            binding.tvLocation.text = location
        }
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.imgChangeProfile.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .start()
        }
        binding.layoutSend.setOnClickListener {
            if (imageUploaded) {
                updateImage()
            } else {
                updateWithoutImage()
            }
        }
    }

    private fun updateImage() {
        val name = binding.edName.text.toString()
        val userName = binding.edUserName.text.toString()
        val location = binding.edLocation.text.toString()
        val address = binding.edAddress.text.toString()
        withProfileImageApi(
            name,
            userName,
            location,
            address)
    }
    private fun updateWithoutImage() {
        val name = binding.edName.text.toString()
        val userName = binding.edUserName.text.toString()
        val location = binding.edLocation.text.toString()
        val address = binding.edAddress.text.toString()
        updateWithoutImageApi(
            name,
            userName,
            location,
            address
        )
    }

    private fun updateWithoutImageApi(
        name: String,
        userName: String,
        location: String,
        address: String
    ) {
        if (utilities.isConnectingToInternet(this@EditProfileActivity)) {
            utilities.showProgressDialog(this@EditProfileActivity, "Please wait...")
            apiClient.getApiService().updateProfile(
                userId, name, userName,
                 "",
                "", "", "", "",
                "", "", "",
                "", "", "",
                "", "", "",
                "", "", "", location, address, ""
            )
                .enqueue(object : Callback<SignupResponseModel> {

                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        val signupResponse = response.body()
                        utilities.hideProgressDialog()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!.equals(true)) {
                                utilities.showSuccessToast(this@EditProfileActivity, signupResponse.message)
                                val gson = Gson()
                                val json = gson.toJson(signupResponse.data)
                                utilities.saveString(this@EditProfileActivity, "loginResponse", json)
                                profileImage = signupResponse.data.profile_image
                                username = signupResponse.data.username
                                val name1 = signupResponse.data.name
                                val location1 = signupResponse.data.location
                                val address1 = signupResponse.data.address
                                Glide.with(this@EditProfileActivity).load(profileImage).into(binding.profileImage)
                                binding.edName.text = Editable.Factory.getInstance().newEditable(name1)
                                binding.edUserName.text = Editable.Factory.getInstance().newEditable(username)
                                binding.edLocation.text = Editable.Factory.getInstance().newEditable(location1)
                                binding.edAddress.text = Editable.Factory.getInstance().newEditable(address1)
                                binding.userName.text = username
                                binding.tvLocation.text = location1
                            } else {
                                utilities.showFailureToast(
                                    this@EditProfileActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@EditProfileActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        utilities.hideProgressDialog()
                        utilities.showFailureToast(this@EditProfileActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@EditProfileActivity)
        }
    }
    fun withProfileImageApi(
        name: String, userName: String,
        location: String, address: String

    ) {
        val apiClient: ApiClient = ApiClient()
        val userId: RequestBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val name: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val userName: RequestBody = userName.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val countryCode: RequestBody = "".toRequestBody("text/plain".toMediaTypeOrNull())
        val location: RequestBody = location.toRequestBody("text/plain".toMediaTypeOrNull())
        val address: RequestBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBody: RequestBody = RequestBody.create("*/*".toMediaTypeOrNull(), imagefile!!)
        imageFileToUpload = MultipartBody.Part.createFormData("profile_image", imagefile!!.name, requestBody)
        utilities.showProgressDialog(this@EditProfileActivity,"Please wait...")
        apiClient.getApiService().editProfile(
            userId,
            name,
            userName,
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
            location,
            address,
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
                            utilities.showSuccessToast(this@EditProfileActivity,message)
                            val gson = Gson()
                            val json = gson.toJson(signupResponse!!.data)
                            utilities.saveString(this@EditProfileActivity, "loginResponse", json)
                            profileImage = signupResponse.data.profile_image
                            username = signupResponse.data.username
                            val name1 = signupResponse.data.name
                            val location1 = signupResponse.data.location
                            val address1 = signupResponse.data.address
                            Glide.with(this@EditProfileActivity).load(profileImage).into(binding.profileImage)
                            binding.edName.text = Editable.Factory.getInstance().newEditable(name1)
                            binding.edUserName.text = Editable.Factory.getInstance().newEditable(username)
                            binding.edLocation.text = Editable.Factory.getInstance().newEditable(location1)
                            binding.edAddress.text = Editable.Factory.getInstance().newEditable(address1)
                            binding.userName.text = username
                            binding.tvLocation.text = location1


                        } else {
                            val message: String = response.body()!!.message

                            utilities.showFailureToast(this@EditProfileActivity,message.toString())

                        }
                    } else {
                        val message: String = response.body()!!.message
                        utilities.showFailureToast(this@EditProfileActivity,message.toString())

                    }
                }

                override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                    utilities.hideProgressDialog()
                    utilities.showFailureToast(this@EditProfileActivity,t.message.toString())

                }
            })


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
            imageUploaded =true


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            utilities.showFailureToast(this@EditProfileActivity,ImagePicker.getError(data))

        } else {
            //do nothing here

        }
    }
}