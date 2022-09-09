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
import com.easylife.easylifes.model.trainerdetail.TrainerDetailResponseModel
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
    private var reviewsList: ArrayList<ReviewDataListModel> = ArrayList()
    private var emptyList: ArrayList<ReviewDataListModel> = ArrayList()
    private var list  : ArrayList<JobsDataModel> = ArrayList()
    var id = ""
    var userId = ""
    var page: Int = 1
    var limit: Int = 10
    var trainerId = ""
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
        page = 1
        limit = 10
        getTrainerDetail()

        list.add(JobsDataModel(R.drawable.workoutfullmage,""))
        list.add(JobsDataModel(R.drawable.workoutfullmage,""))
        list.add(JobsDataModel(R.drawable.workoutfullmage,""))
        list.add(JobsDataModel(R.drawable.workoutfullmage,""))
        list.add(JobsDataModel(R.drawable.workoutfullmage,""))
        list.add(JobsDataModel(R.drawable.workoutfullmage,""))
        list.add(JobsDataModel(R.drawable.workoutfullmage,""))
        list.add(JobsDataModel(R.drawable.workoutfullmage,""))
        list.add(JobsDataModel(R.drawable.workoutfullmage,""))
        binding.rvVideos.layoutManager = GridLayoutManager(this@InstructorDetailActivity,2)
        binding.rvVideos.adapter = TrainerVideosAdapter(this@InstructorDetailActivity,list)



        /*This code is commented becuase now we are not shwoing reviews in the detial screen here*/
        /*binding.idNestedSV.getViewTreeObserver().addOnScrollChangedListener(OnScrollChangedListener {
            val view = binding.idNestedSV.getChildAt(binding.idNestedSV.getChildCount() - 1) as View
            val diff: Int = view.bottom - (binding.idNestedSV.getHeight() + binding.idNestedSV.getScrollY())
            if (diff == 0) {
                // your pagination code
                page++
                binding.idPBLoading.setVisibility(View.VISIBLE)
                getTrainerDetail2()
            }
        })*/

    }


    private fun getTrainerDetail() {
        if (page > limit) {
            // checking if the page number is greater than limit.
            // displaying toast message in this case when page>limit.
            // Toast.makeText(requireContext(), "That's all the data..", Toast.LENGTH_SHORT).show();
            //utils.customToastSuccess(resources.getString(R.string.thatsalldata),this@MyListingActivity)

            // hiding our progress bar.
            binding.idPBLoading.setVisibility(View.GONE);
            return;
        }
        val apiClient: ApiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@InstructorDetailActivity)) {
            val gsonn = Gson()
            val jsonn: String =
                utilities.getString(this@InstructorDetailActivity, "loginResponse").toString()
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val user_idd: String = java.lang.String.valueOf(obj.id)

            utilities.showProgressDialog(this@InstructorDetailActivity, "Loading Data...")
            val url = apiClient.BASE_URL + "trainer-details/" + user_idd + "/" + id+"?page=" + page
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

                            /*No Review is adding now*/
                            /*reviewsList = signupResponse.data.reviews.data
                            limit = response.body()!!.data.reviews.last_page
                            emptyList.addAll(reviewsList)
                            if (emptyList.isEmpty()) {
                                binding.idNestedSV.visibility = View.GONE
                                binding.tvNoViewFound.visibility = View.VISIBLE
                                binding.idPBLoading.visibility = View.GONE
                            }

                            binding.rvReviews.layoutManager = LinearLayoutManager(
                                this@InstructorDetailActivity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.rvReviews.adapter = ReviewsAdapter(this@InstructorDetailActivity, emptyList)
                            binding.idPBLoading.visibility = View.GONE*/
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





    private fun getTrainerDetail2() {
        if (utilities.isConnectingToInternet(this@InstructorDetailActivity)) {
            if (page > limit) {
                // checking if the page number is greater than limit.
                // displaying toast message in this case when page>limit.
                // Toast.makeText(requireContext(), "That's all the data..", Toast.LENGTH_SHORT).show();
                //utils.customToastSuccess(resources.getString(R.string.thatsalldata),this@MyListingActivity)

                // hiding our progress bar.
                binding.idPBLoading.setVisibility(View.GONE);
                return;
            }
            val apiClient: ApiClient = ApiClient()
            if (utilities.isConnectingToInternet(this@InstructorDetailActivity)) {
                val gsonn = Gson()
                val jsonn: String =
                    utilities.getString(this@InstructorDetailActivity, "loginResponse").toString()
                val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
                val user_idd: String = java.lang.String.valueOf(obj.id)

                val url = apiClient.BASE_URL + "trainer-details/" + user_idd + "/" + id + "?page=" + page
                apiClient.getApiService().trainerDetail(url)
                    .enqueue(object : Callback<TrainerDetailResponseModel> {

                        override fun onResponse(
                            call: Call<TrainerDetailResponseModel>,
                            response: Response<TrainerDetailResponseModel>
                        ) {
                            val signupResponse = response.body()

                            if (signupResponse!!.status == true) {
                                reviewsList = signupResponse.data.reviews.data
                                limit = response.body()!!.data.reviews.last_page
                                emptyList.addAll(reviewsList)
                                if (emptyList.isEmpty()) {
                                    binding.idNestedSV.visibility = View.GONE
                                    binding.tvNoViewFound.visibility = View.VISIBLE
                                    binding.idPBLoading.visibility = View.GONE
                                }
                                binding.rvReviews.layoutManager = LinearLayoutManager(
                                    this@InstructorDetailActivity,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                binding.rvReviews.adapter =
                                    ReviewsAdapter(this@InstructorDetailActivity, emptyList)
                                //  utilitiess.customToastSuccess(signupResponse.message, context!!)
                            } else {
                                utilities.showFailureToast(
                                    this@InstructorDetailActivity,
                                    signupResponse.message,
                                )


                            }


                        }

                        override fun onFailure(
                            call: Call<TrainerDetailResponseModel>,
                            t: Throwable
                        ) {
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
}