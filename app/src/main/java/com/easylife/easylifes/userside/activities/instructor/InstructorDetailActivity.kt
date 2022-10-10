package com.easylife.easylifes.userside.activities.instructor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityInstructorDetailBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.trainerdetail.ReviewDataListModel
import com.easylife.easylifes.model.trainerdetail.SubscriptionPackageDataModel
import com.easylife.easylifes.model.trainerdetail.TrainerDetailResponseModel
import com.easylife.easylifes.model.trainerdetail.VideoListDataModel
import com.easylife.easylifes.userside.activities.choosepackage.ChooseYourPackage
import com.easylife.easylifes.userside.activities.review.WriteReviewActivity
import com.easylife.easylifes.userside.adapter.ReviewsAdapter
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
    lateinit var videoList : ArrayList<VideoListDataModel>
    lateinit var packageList : ArrayList<SubscriptionPackageDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        utilities = Utilities(this@InstructorDetailActivity)
        utilities.setGreenBar(this@InstructorDetailActivity)

        val intent = intent
        id = intent.getStringExtra("id").toString()
        binding.tvAddAReviews.setOnClickListener {
            startActivity(Intent(this@InstructorDetailActivity, WriteReviewActivity::class.java))
        }
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.tvAddAReviews.setOnClickListener {
            val intent1 = Intent(this@InstructorDetailActivity,WriteReviewActivity::class.java)
            intent1.putExtra("id",trainerId)
            startActivity(intent1)
            finish()
        }
        binding.rlSubscribe.setOnClickListener {
            if (packageList.isEmpty())
            {
                utilities.showFailureToast(this@InstructorDetailActivity,"Loading Packages")
            }else{
                val intent = Intent(this@InstructorDetailActivity,ChooseYourPackage::class.java)
                intent.putParcelableArrayListExtra("packagelist",packageList)
                startActivity(intent)
            }

        }


        getTrainerDetail()


    }


    private fun getTrainerDetail() {

        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@InstructorDetailActivity)) {
            val gsonn = Gson()
            val jsonn: String =
                utilities.getString(this@InstructorDetailActivity, "loginResponse").toString()
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val user_idd: String = java.lang.String.valueOf(obj.id)


            val url = apiClient.BASE_URL + "trainer-details/" + user_idd + "/" + id
            apiClient.getApiService().trainerDetail(url)
                .enqueue(object : Callback<TrainerDetailResponseModel> {

                    override fun onResponse(
                        call: Call<TrainerDetailResponseModel>,
                        response: Response<TrainerDetailResponseModel>
                    ) {
                        val signupResponse = response.body()
                        utilities.hideProgressDialog()

                        if (signupResponse!!.status == true) {
                            Glide.with(this@InstructorDetailActivity)
                                .load(signupResponse.data.profile_image).into(binding.trainerPic)
                            binding.tvTrainerName.text = signupResponse.data.name
                            binding.tvSpeciality.text = signupResponse.data.specialization
                            binding.tvWorkExperience.text = signupResponse.data.experience
                            binding.tvCompleteWorkout.text = signupResponse.data.completed_workouts
                            binding.tvClients.text = signupResponse.data.active_clients
                            trainerId = signupResponse.data.id.toString()


                            videoList = ArrayList()
                            videoList = signupResponse.data.videos
                            binding.rvVideos.layoutManager = GridLayoutManager(this@InstructorDetailActivity,2)
                            binding.rvVideos.adapter = TrainerVideosAdapter(this@InstructorDetailActivity,videoList)


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





}