package com.easylife.easylifes.trainerside.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.easylife.easylifes.BuildConfig
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.FragmentTrainerProfileBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.trainerside.activities.profile.EditTrainerProfileActivity
import com.easylife.easylifes.userside.activities.Support.SupportActivity
import com.easylife.easylifes.userside.activities.auth.LogoutActivity
import com.easylife.easylifes.userside.activities.choosepackage.MyPaymentsActivity
import com.easylife.easylifes.userside.activities.subscribedtrainer.SubscribedTrainerActivity
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


class TrainerProfileFragment : Fragment() {
    private lateinit var binding : FragmentTrainerProfileBinding
    private lateinit var utilities: Utilities
    var profileImage = ""
    var username = ""
    var userid = ""
    var location = ""
    private var imageUploaded: Boolean = false
    val apiClient=  ApiClient()
    private var imagefile: File? = null
    private lateinit var imageFileToUpload: MultipartBody.Part
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentTrainerProfileBinding.inflate(inflater,container,false)


        initViews()
        statusbarColor()
        onClicks()
        return binding.root
    }

    private fun initViews() {
       utilities  = Utilities(requireContext())
        val gsonn = Gson()
        val jsonn: String = utilities.getString(requireContext(), "loginResponse")
        val drawable = CircularProgressDrawable(requireContext())
        drawable.setColorSchemeColors(R.color.appColor, R.color.appColor, R.color.appColor)
        drawable.centerRadius = 25f
        drawable.strokeWidth = 6f
        drawable.start()
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileImage = obj.profile_image
            userid = obj.id.toString()
            username = obj.username
            location = obj.location
            Glide.with(requireContext()).load(profileImage).placeholder(drawable).into(binding.shapeableImageView)
            binding.tvusername.text = username
            binding.tvLocation.text = location

        }

        binding.imgChangeProfile.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .start()
        }


    }


    //imagepicker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            binding.shapeableImageView.setImageURI(uri)
            val photoFile = File(uri.path!!)
            imagefile = photoFile
            imageUploaded =true
            if (imageUploaded)
            {
                withProfileImageApi()
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            utilities.showFailureToast(requireActivity(),ImagePicker.getError(data))

        } else {
            //do nothing here

        }
    }

    private fun withProfileImageApi(

    ) {
        val apiClient = ApiClient()
        val userId: RequestBody = userid.toRequestBody("text/plain".toMediaTypeOrNull())
//        val name: RequestBody = "".toRequestBody("text/plain".toMediaTypeOrNull())
//        val userName: RequestBody = "".toRequestBody("text/plain".toMediaTypeOrNull())
        val countryCode: RequestBody = "".toRequestBody("text/plain".toMediaTypeOrNull())
//        val location: RequestBody = "".toRequestBody("text/plain".toMediaTypeOrNull())
//        val address: RequestBody = "".toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBody: RequestBody = imagefile!!.asRequestBody("*/*".toMediaTypeOrNull())
        imageFileToUpload = MultipartBody.Part.createFormData("profile_image", imagefile!!.name, requestBody)
        binding.dotloader.visibility  =View.VISIBLE
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
                    binding.dotloader.visibility = View.GONE
                    if (isAdded)
                    {
                        if (response.isSuccessful) {
                            val status: Boolean = response.body()!!.status
                            if (status) {
                                val signupResponse = response.body()
                                val message: String = response.body()!!.message
                                utilities.showSuccessToast(requireActivity(),message)
                                val gson = Gson()
                                val json = gson.toJson(signupResponse!!.data)
                                utilities.saveString(requireContext(), "loginResponse", json)


                            } else {
                                val message: String = response.body()!!.message

                                utilities.showFailureToast(requireActivity(),message)

                            }
                        } else {
                            val message: String = response.body()!!.message
                            utilities.showFailureToast(requireActivity(),message)

                        }
                    }

                }

                override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                    binding.dotloader.visibility =View.GONE
                    if (isAdded)
                    {
                        utilities.showFailureToast(requireActivity(),t.message.toString())
                    }
                }
            })


    }
    private fun statusbarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.statusBarColor = requireActivity().getColor(R.color.haiti)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController!!
                .setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        }
    }

    private fun onClicks() {
        binding.cardGoals.setOnClickListener {
            binding.rlMyGoals.setBackgroundResource(R.color.appColor)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_blue)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.tvGoals.setTextColor(resources.getColor(R.color.white))
            binding.rlBodyMeasurement.setBackgroundResource(R.color.white)
            binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_white)
            binding.imgBodyMeasurements.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.haiti))
            binding.rlMyPayments.setBackgroundResource(R.color.white)
            binding.rlMyPayments2.setBackgroundResource(R.drawable.round_white)
            binding.imgMyPayments.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvMyPayments.setTextColor(resources.getColor(R.color.haiti))
            binding.rlInviteFriend.setBackgroundResource(R.color.white)
            binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_white)
            binding.imgInviteFriend.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvInviteFriend.setTextColor(resources.getColor(R.color.haiti))
            binding.rlEditAccount.setBackgroundResource(R.color.white)
            binding.rlEditAccount2.setBackgroundResource(R.drawable.round_white)
            binding.imgEditAccount.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvEditAccount.setTextColor(resources.getColor(R.color.haiti))
            startActivity(Intent(requireContext(),MyPaymentsActivity::class.java))
        }
        binding.cardBodyMeasurements.setOnClickListener {
            binding.rlMyGoals.setBackgroundResource(R.color.white)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvGoals.setTextColor(resources.getColor(R.color.haiti))
            binding.rlBodyMeasurement.setBackgroundResource(R.color.appColor)
            binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_blue)
            binding.imgBodyMeasurements.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.white))
            binding.rlMyPayments.setBackgroundResource(R.color.white)
            binding.rlMyPayments2.setBackgroundResource(R.drawable.round_white)
            binding.imgMyPayments.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvMyPayments.setTextColor(resources.getColor(R.color.haiti))
            binding.rlInviteFriend.setBackgroundResource(R.color.white)
            binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_white)
            binding.imgInviteFriend.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvInviteFriend.setTextColor(resources.getColor(R.color.haiti))
            binding.rlEditAccount.setBackgroundResource(R.color.white)
            binding.rlEditAccount2.setBackgroundResource(R.drawable.round_white)
            binding.imgEditAccount.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvEditAccount.setTextColor(resources.getColor(R.color.haiti))

            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Tabadoul")
                var shareMessage = "\nLet me recommend you this application::\n"
                shareMessage =
                    """
                    ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                    """.trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }

        }
        binding.cardEditProfile.setOnClickListener {

            binding.rlMyGoals.setBackgroundResource(R.color.white)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
            binding.imgGoalsIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.statusbarcolor))
            binding.tvGoals.setTextColor(resources.getColor(R.color.haiti))
            binding.rlBodyMeasurement.setBackgroundResource(R.color.white)
            binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_white)
            binding.imgBodyMeasurements.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.haiti))
            binding.rlMyPayments.setBackgroundResource(R.color.appColor)
            binding.rlMyPayments2.setBackgroundResource(R.drawable.round_blue)
            binding.imgMyPayments.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.tvMyPayments.setTextColor(resources.getColor(R.color.white))
            binding.rlInviteFriend.setBackgroundResource(R.color.white)
            binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_white)
            binding.imgInviteFriend.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvInviteFriend.setTextColor(resources.getColor(R.color.haiti))

            binding.rlEditAccount.setBackgroundResource(R.color.white)
            binding.rlEditAccount2.setBackgroundResource(R.drawable.round_white)
            binding.imgEditAccount.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvEditAccount.setTextColor(resources.getColor(R.color.haiti))
            startActivity(Intent(requireContext(), EditTrainerProfileActivity::class.java))

        }
        binding.cardInviteFriend.setOnClickListener {
            binding.rlMyGoals.setBackgroundResource(R.color.white)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvGoals.setTextColor(resources.getColor(R.color.haiti))
            binding.rlBodyMeasurement.setBackgroundResource(R.color.white)
            binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_white)
            binding.imgBodyMeasurements.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.haiti))
            binding.rlMyPayments.setBackgroundResource(R.color.white)
            binding.rlMyPayments2.setBackgroundResource(R.drawable.round_white)
            binding.imgMyPayments.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvMyPayments.setTextColor(resources.getColor(R.color.haiti))
            binding.rlInviteFriend.setBackgroundResource(R.color.appColor)
            binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_blue)
            binding.imgInviteFriend.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.tvInviteFriend.setTextColor(resources.getColor(R.color.white))

            binding.rlEditAccount.setBackgroundResource(R.color.white)
            binding.rlEditAccount2.setBackgroundResource(R.drawable.round_white)
            binding.imgEditAccount.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvEditAccount.setTextColor(resources.getColor(R.color.haiti))
            startActivity(Intent(requireContext(),LogoutActivity::class.java))
        }

        binding.cardEditAccount.setOnClickListener {
            binding.rlMyGoals.setBackgroundResource(R.color.white)
            binding.rlMyGoals2.setBackgroundResource(R.drawable.round_white)
            binding.imgGoalsIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvGoals.setTextColor(resources.getColor(R.color.haiti))
            binding.rlBodyMeasurement.setBackgroundResource(R.color.white)
            binding.rlBodyMeasurement2.setBackgroundResource(R.drawable.round_white)
            binding.imgBodyMeasurements.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvBodyMeasurements.setTextColor(resources.getColor(R.color.haiti))
            binding.rlMyPayments.setBackgroundResource(R.color.white)
            binding.rlMyPayments2.setBackgroundResource(R.drawable.round_white)
            binding.imgMyPayments.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvMyPayments.setTextColor(resources.getColor(R.color.haiti))
            binding.rlInviteFriend.setBackgroundResource(R.color.white)
            binding.rlInviteFriend2.setBackgroundResource(R.drawable.round_white)
            binding.imgInviteFriend.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.statusbarcolor
                )
            )
            binding.tvInviteFriend.setTextColor(resources.getColor(R.color.haiti))

            binding.rlEditAccount.setBackgroundResource(R.color.appColor)
            binding.rlEditAccount2.setBackgroundResource(R.drawable.round_blue)
            binding.imgEditAccount.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.tvEditAccount.setTextColor(resources.getColor(R.color.white))

            startActivity(Intent(requireContext(),SupportActivity::class.java))


        }

    }
}