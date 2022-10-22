package com.easylife.easylifes.userside.activities.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityUserProfileBinding
import com.easylife.easylifes.model.faq.FaqResponseModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.userside.adapter.FaqAdapter
import com.easylife.easylifes.utils.Utilities
import com.tabadol.tabadol.data.network.ApiClient
import kotlinx.android.synthetic.main.activity_macronutrients_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserProfileBinding
    private lateinit var utilities: Utilities
    var userId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()

    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@UserProfileActivity)
        utilities.setGrayBar(this@UserProfileActivity)

        val intent = intent
        userId = intent.getStringExtra("id").toString()
        userProfile()

    }

    private fun userProfile() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@UserProfileActivity)) {

            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "user-profile/"+userId
            apiClient.getApiService().userProfile(url)
                .enqueue(object : Callback<SignupResponseModel> {

                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (signupResponse!!.status) {
                            Glide.with(this@UserProfileActivity).load(signupResponse.data.profile_image).into(binding.profileImage)
                            binding.userName.text  = signupResponse.data.name
                            binding.edName.text = signupResponse.data.name
                            binding.edUserName.text = signupResponse.data.username
                            binding.tvPhoneNumber.text = signupResponse.data.country_code+signupResponse.data.phone
                            binding.edEmail.text = signupResponse.data.email
                            binding.tvGender.text = signupResponse.data.gender
                            binding.tvAge.text = signupResponse.data.age
                            binding.tvWeight.text = signupResponse.data.weight+signupResponse.data.weight_unit
                            binding.tvHeight.text = signupResponse.data.height+signupResponse.data.height_unit
                            binding.tvLocation.text = signupResponse.data.location
                            binding.edAddress.text = signupResponse.data.address
                            binding.tvGoal.text = signupResponse.data.your_goal
                            binding.tvCurrentFitnessLevel.text = signupResponse.data.current_fitness_level

                        } else {
                            utilities.showFailureToast(
                                this@UserProfileActivity,
                                signupResponse.message
                            )


                        }


                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@UserProfileActivity, t.message!!)
                    }


                })


        } else {

            utilities.showNoInternetToast(this@UserProfileActivity)

        }

    }

}