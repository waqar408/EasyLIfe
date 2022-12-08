package com.easylife.easylifes.userside.activities.instructor

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityInstructorDetailBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.trainerdetail.SubscriptionPackageDataModel
import com.easylife.easylifes.model.trainerdetail.TrainerDetailResponseModel
import com.easylife.easylifes.model.trainerdetail.VideoListDataModel
import com.easylife.easylifes.trainerside.activities.clientdetail.WorkoutSelectionActivity
import com.easylife.easylifes.userside.activities.auth.LoginActivity
import com.easylife.easylifes.userside.activities.choosepackage.ChooseYourPackage
import com.easylife.easylifes.userside.activities.inbox.InboxActivity
import com.easylife.easylifes.userside.activities.review.ReviewListActivity
import com.easylife.easylifes.userside.activities.review.WriteReviewActivity
import com.easylife.easylifes.userside.adapter.TrainerVideosAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InstructorDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInstructorDetailBinding
    private lateinit var utilities: Utilities
    var id = ""
    var userId = ""
    var trainerId = ""
    var trainerProfileImage = ""
    var trainerName = ""
    var trainerNickName = ""
    var type = ""
    var isSubscribed: Int? = null
    lateinit var videoList: ArrayList<VideoListDataModel>
    lateinit var packageList: ArrayList<SubscriptionPackageDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        utilities = Utilities(this@InstructorDetailActivity)
        utilities.setGreenBar(this@InstructorDetailActivity)
        val gsonn = Gson()
        val jsonn: String =
            utilities.getString(this@InstructorDetailActivity, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        userId = java.lang.String.valueOf(obj.id)
        val intent = intent
        id = intent.getStringExtra("id").toString()
        type = obj.type

        binding.tvAddAReviews.setOnClickListener {
            startActivity(Intent(this@InstructorDetailActivity, WriteReviewActivity::class.java))
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.tvAddAReviews.setOnClickListener {
            val intent1 = Intent(this@InstructorDetailActivity, WriteReviewActivity::class.java)
            intent1.putExtra("id", trainerId)
            startActivity(intent1)
            finish()
        }
        binding.rlSubscribe.setOnClickListener {
            /*if (packageList.isEmpty()) {
                utilities.showFailureToast(this@InstructorDetailActivity, "Loading Packages")
            } else {
                if (isSubscribed== 1)
                {
                    utilities.showSuccessToast(this@InstructorDetailActivity,"You are already subsribed")
                }else{
                    val intentChoosePackage = Intent(this@InstructorDetailActivity, ChooseYourPackage::class.java)
                    intentChoosePackage.putParcelableArrayListExtra("packagelist", packageList)
                    startActivity(intentChoosePackage)
                }

            }*/
            if (type == "3") {
                guestDialog()
            } else {
                val intentChoosePackage =
                    Intent(this@InstructorDetailActivity, ChooseYourPackage::class.java)
                intentChoosePackage.putParcelableArrayListExtra("packagelist", packageList)
                startActivity(intentChoosePackage)
            }

        }
        binding.rlChat.setOnClickListener {
            if (type == "3") {
                guestDialog()
            } else {
                val intentInbox = Intent(this@InstructorDetailActivity, InboxActivity::class.java)
                intentInbox.putExtra("myId", userId)
                intentInbox.putExtra("otherUserId", trainerId)
                intentInbox.putExtra("otherUserProfile", trainerProfileImage)
                intentInbox.putExtra("otherUserName", trainerName)
                intentInbox.putExtra("otherUserNicName", trainerNickName)
                startActivity(intentInbox)
            }


        }

        binding.rlReviews1.setOnClickListener {
            if (type == "3") {
                guestDialog()
            } else {
                val intentReview =
                    Intent(this@InstructorDetailActivity, ReviewListActivity::class.java)
                intentReview.putExtra("trainerid", trainerId)
                startActivity(intentReview)
            }

        }


        getTrainerDetail()


    }


    private fun getTrainerDetail() {

        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@InstructorDetailActivity)) {
            val gsonn = Gson()
            val jsonn: String =
                utilities.getString(this@InstructorDetailActivity, "loginResponse")
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val userId: String = java.lang.String.valueOf(obj.id)


            val url = apiClient.BASE_URL + "trainer-details/" + userId + "/" + id
            apiClient.getApiService().trainerDetail(url)
                .enqueue(object : Callback<TrainerDetailResponseModel> {

                    override fun onResponse(
                        call: Call<TrainerDetailResponseModel>,
                        response: Response<TrainerDetailResponseModel>
                    ) {
                        val signupResponse = response.body()
                        utilities.hideProgressDialog()

                        if (signupResponse!!.status) {
                            isSubscribed = signupResponse.data.is_subscribed
                            Glide.with(this@InstructorDetailActivity)
                                .load(signupResponse.data.profile_image).into(binding.trainerPic)
                            binding.tvTrainerName.text = signupResponse.data.name
                            binding.tvSpeciality.text = signupResponse.data.specialization
                            binding.tvWorkExperience.text = signupResponse.data.experience
                            binding.tvCompleteWorkout.text = signupResponse.data.completed_workouts
                            binding.tvClients.text = signupResponse.data.active_clients
                            trainerId = signupResponse.data.id.toString()
                            trainerProfileImage = signupResponse.data.profile_image
                            trainerName = signupResponse.data.name
                            trainerNickName = signupResponse.data.username
                            binding.tvRating.text = signupResponse.data.average_rating

                            if (isSubscribed == 1) {
                                binding.tvIsSubscribe.text = "Subscribed"
                            } else {
                                binding.tvIsSubscribe.text = "Subscribe"
                            }


                            videoList = ArrayList()
                            videoList = signupResponse.data.videos
                            binding.rvVideos.layoutManager =
                                GridLayoutManager(this@InstructorDetailActivity, 2)
                            binding.rvVideos.adapter =
                                TrainerVideosAdapter(this@InstructorDetailActivity, videoList)


                            packageList = ArrayList()
                            packageList = signupResponse.data.subscription_packages


                        } else {
                            utilities.hideProgressDialog()
                            utilities.showFailureToast(
                                this@InstructorDetailActivity,
                                signupResponse.message,
                            )
                        }


                    }

                    override fun onFailure(call: Call<TrainerDetailResponseModel>, t: Throwable) {
                        utilities.hideProgressDialog()
                        utilities.showFailureToast(this@InstructorDetailActivity, t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(
                this@InstructorDetailActivity,
                resources.getString(R.string.checkinternet)
            )

        }

    }

    private fun guestDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_guestmode)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes = lp
        val layoutsend = dialog.findViewById<RelativeLayout>(R.id.layout_send)
        val imgClose = dialog.findViewById<ImageView>(R.id.imgClose)


        layoutsend.setOnClickListener {
            val intent = Intent(this@InstructorDetailActivity, LoginActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        imgClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}