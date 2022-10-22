package com.easylife.easylifes.userside.activities.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayurmitra.ayurmitra.model.reviews.ReviewDetailDataModel
import com.ayurmitra.ayurmitra.model.reviews.ReviewResponseModel
import com.easylife.easylifes.databinding.ActivityReviewListBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.userside.adapter.ReviewsAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewListBinding
    private lateinit var reviewList: ArrayList<ReviewDetailDataModel>
    private lateinit var emptyList: ArrayList<ReviewDetailDataModel>
    private lateinit var utilities: Utilities
    private lateinit var adapter: ReviewsAdapter
    var userId = ""
    private var page: Int = 1
    var limit: Int = 10
    var trainerId = ""
    lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()

    }

    private fun initViews() {
        utilities = Utilities(this@ReviewListActivity)
        utilities.setGrayBar(this@ReviewListActivity)
        utilities = Utilities(this@ReviewListActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@ReviewListActivity, "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = java.lang.String.valueOf(obj.id)
        }
        val intent =intent
        trainerId = intent.getStringExtra("trainerid").toString()
        emptyList = ArrayList()
        reviewList = ArrayList()
        apiClient = ApiClient()
        page = 1
        limit = 10
        reviewList()
        binding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                reviewList()
            }
        })

        binding.imgWriteReviews.setOnClickListener {
            val intent = Intent(this@ReviewListActivity,WriteReviewActivity::class.java)
            intent.putExtra("trainerid",trainerId.toString())
            startActivity(intent)
            Log.d("trainerid",trainerId)
        }
    }

    private fun reviewList() {
        if (utilities.isConnectingToInternet(this@ReviewListActivity)) {
            if (page > limit) {

                binding.idPBLoading.visibility = View.GONE
                return
            }
            val url = apiClient.BASE_URL + "trainer-reviews/" + trainerId + "?page=" + page
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().trainerReviewsList(url)
                .enqueue(object : Callback<ReviewResponseModel> {

                    override fun onResponse(
                        call: Call<ReviewResponseModel>,
                        response: Response<ReviewResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (response.isSuccessful) {

                            if (signupResponse?.status!!) {

                                reviewList = signupResponse.data.reviewDetailDataModel
                                limit = response.body()!!.data.last_page
                                emptyList.addAll(reviewList)
                                if (emptyList.isEmpty()) {
                                    binding.idNestedSV.visibility = View.GONE
                                    binding.tvNoViewFound.visibility = View.VISIBLE
                                    binding.idPBLoading.visibility = View.GONE
                                }
                                binding.rvReview.layoutManager = LinearLayoutManager(
                                    this@ReviewListActivity,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                adapter =
                                    ReviewsAdapter(this@ReviewListActivity, emptyList)
                                binding.rvReview.adapter = adapter
                                binding.rvReview.isNestedScrollingEnabled = false

                            } else {
                                utilities.showFailureToast(
                                    this@ReviewListActivity,
                                    signupResponse.message
                                )
                            }


                        } else {
                            utilities.showFailureToast(
                                this@ReviewListActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<ReviewResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        //   utilities.showFailureToast(requireActivity(), t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@ReviewListActivity)
        }
    }

}