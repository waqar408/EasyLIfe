package com.easylife.easylifes.trainerside.activities.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.WindowInsetsController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityEditTrainerProfileBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.trainerside.activities.TrainerPortfolioActivity
import com.easylife.easylifes.utils.Utilities
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditTrainerProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditTrainerProfileBinding
    private lateinit var utilities: Utilities
    var profileImage = ""
    var username =""
    var name = ""
    var location = ""
    var address  = ""
    var userId = ""
    private var phoneCode = ""
    private var phoneNumber = ""
    private var imageUploaded: Boolean = false
    val apiClient=  ApiClient()
    private var imagefile: File? = null
    private lateinit var imageFileToUpload: MultipartBody.Part
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTrainerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
        statusbarColor()
    }

    private fun initViews() {
        utilities = Utilities(this@EditTrainerProfileActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        val drawable = CircularProgressDrawable(this@EditTrainerProfileActivity)
        drawable.setColorSchemeColors(R.color.appColor, R.color.appColor, R.color.appColor)
        drawable.centerRadius = 25f
        drawable.strokeWidth = 6f
        drawable.start()
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileImage = obj.profile_image
            userId = obj.id.toString()
            username = obj.username
            name = obj.name
            location = obj.location
            address = obj.address
            phoneCode = obj.country_code
            phoneNumber= obj.phone
            val experience = obj.experience
            Glide.with(this@EditTrainerProfileActivity).load(profileImage).placeholder(drawable).into(binding.profileImage)
            binding.edName.text = Editable.Factory.getInstance().newEditable(name)
            binding.edUserName.text = Editable.Factory.getInstance().newEditable(username)
            binding.tvPhoneNumber.text = "$phoneCode $phoneNumber"
            binding.edLocation.text = Editable.Factory.getInstance().newEditable(location)
            binding.edAddress.text = Editable.Factory.getInstance().newEditable(address)
            binding.userName.text = username
            binding.tvLocation.text = location
            binding.edExperience.text = Editable.Factory.getInstance().newEditable(experience)
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
        binding.layoutPorfolio.setOnClickListener {
            startActivity(Intent(this@EditTrainerProfileActivity,TrainerPortfolioActivity::class.java))
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
        if (utilities.isConnectingToInternet(this@EditTrainerProfileActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().updateProfile(
                userId, name, userName,
                "",
                "", "", "", "",
                "", "", "",
                "", "", "",
                "", "", "",
                "", "", "", location, address, "","","","","","",""
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
                                utilities.showSuccessToast(this@EditTrainerProfileActivity, signupResponse.message)
                                val gson = Gson()
                                val json = gson.toJson(signupResponse.data)
                                utilities.saveString(this@EditTrainerProfileActivity, "loginResponse", json)
                                profileImage = signupResponse.data.profile_image
                                username = signupResponse.data.username
                                val name1 = signupResponse.data.name
                                val location1 = signupResponse.data.location
                                val address1 = signupResponse.data.address
                                Glide.with(this@EditTrainerProfileActivity).load(profileImage).into(binding.profileImage)
                                binding.edName.text = Editable.Factory.getInstance().newEditable(name1)
                                binding.edUserName.text = Editable.Factory.getInstance().newEditable(username)
                                binding.edLocation.text = Editable.Factory.getInstance().newEditable(location1)
                                binding.edAddress.text = Editable.Factory.getInstance().newEditable(address1)
                                binding.userName.text = username
                                binding.tvLocation.text = location1
                            } else {
                                utilities.showFailureToast(
                                    this@EditTrainerProfileActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@EditTrainerProfileActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@EditTrainerProfileActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@EditTrainerProfileActivity)
        }
    }
    private fun withProfileImageApi(
        name: String, userName: String,
        location: String, address: String

    ) {
        val apiClient = ApiClient()
        val userId: RequestBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val name1: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val userName1: RequestBody = userName.toRequestBody("text/plain".toMediaTypeOrNull())
        val countryCode: RequestBody = "".toRequestBody("text/plain".toMediaTypeOrNull())
        val location1: RequestBody = location.toRequestBody("text/plain".toMediaTypeOrNull())
        val address1: RequestBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBody: RequestBody = imagefile!!.asRequestBody("*/*".toMediaTypeOrNull())
        imageFileToUpload = MultipartBody.Part.createFormData("profile_image", imagefile!!.name, requestBody)
        binding.dotloader.visibility =  View.VISIBLE
        apiClient.getApiService().editProfile(
            userId,
            name1,
            userName1,
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
            location1,
            address1,
            countryCode,
            imageFileToUpload
        )
            .enqueue(object : Callback<SignupResponseModel> {
                override fun onResponse(
                    call: Call<SignupResponseModel>,
                    response: Response<SignupResponseModel>
                ) {
                    binding.dotloader.visibility = View.GONE
                    if (response.isSuccessful) {
                        val status: Boolean = response.body()!!.status
                        if (status) {
                            val signupResponse = response.body()
                            val message: String = response.body()!!.message
                            utilities.showSuccessToast(this@EditTrainerProfileActivity,message)
                            val gson = Gson()
                            val json = gson.toJson(signupResponse!!.data)
                            utilities.saveString(this@EditTrainerProfileActivity, "loginResponse", json)
                            profileImage = signupResponse.data.profile_image
                            username = signupResponse.data.username
                            val name2 = signupResponse.data.name
                            val location2 = signupResponse.data.location
                            val address2 = signupResponse.data.address
                            Glide.with(this@EditTrainerProfileActivity).load(profileImage).into(binding.profileImage)
                            binding.edName.text = Editable.Factory.getInstance().newEditable(name2)
                            binding.edUserName.text = Editable.Factory.getInstance().newEditable(username)
                            binding.edLocation.text = Editable.Factory.getInstance().newEditable(location2)
                            binding.edAddress.text = Editable.Factory.getInstance().newEditable(address2)
                            binding.userName.text = username
                            binding.tvLocation.text = location2


                        } else {
                            val message: String = response.body()!!.message

                            utilities.showFailureToast(this@EditTrainerProfileActivity,message)

                        }
                    } else {
                        val message: String = response.body()!!.message
                        utilities.showFailureToast(this@EditTrainerProfileActivity,message)

                    }
                }

                override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                    binding.dotloader.visibility = View.GONE
                    utilities.showFailureToast(this@EditTrainerProfileActivity,t.message.toString())

                }
            })


    }

    private fun statusbarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = getColor(R.color.haiti)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController!!
                .setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        }
    }
    //imagepicker
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!
                binding.profileImage.setImageURI(uri)
                val photoFile = File(uri.path!!)
                imagefile = photoFile
                imageUploaded =true


            }
            ImagePicker.RESULT_ERROR -> {
                utilities.showFailureToast(this@EditTrainerProfileActivity, ImagePicker.getError(data))

            }
            else -> {
                //do nothing here

            }
        }
    }
}