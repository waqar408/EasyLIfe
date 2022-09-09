package com.easylife.easylifes.userside.activities.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityWriteReviewBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.trainerdetail.TrainerDetailResponseModel
import com.easylife.easylifes.userside.activities.instructor.InstructorDetailActivity
import com.easylife.easylifes.userside.adapter.ReviewsAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteReviewActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWriteReviewBinding
    var trainerId = ""
    var userId = ""
    var ratingBar = ""
    var review = ""
    private lateinit var utilities: Utilities
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()


    }

    private fun onClicks() {
        binding.tvSubmitReview.setOnClickListener {
            ratingBar = binding.ratingBar1.rating.toString()
            review = binding.edReview.text.toString()
            if (ratingBar.equals("0.0"))
            {
                utilities.showFailureToast(this@WriteReviewActivity,"Please Give Rating...")
            }else if (review.equals("")){
                utilities.showFailureToast(this@WriteReviewActivity,"Please Give Reviews...")
            }else{
                writeReview(ratingBar,review)
            }
        }
        binding.layoutBackArrow.setOnClickListener {
            val intent = Intent(this@WriteReviewActivity,InstructorDetailActivity::class.java)
            intent.putExtra("id",trainerId)
            startActivity(intent)
            finish()
        }
    }

    private fun writeReview(ratingBar: String, review: String) {
        val apiClient: ApiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@WriteReviewActivity)) {
            val gsonn = Gson()
            val jsonn: String =
                utilities.getString(this@WriteReviewActivity, "loginResponse").toString()
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val user_idd: String = java.lang.String.valueOf(obj.id)

            utilities.showProgressDialog(this@WriteReviewActivity, "Loading Data...")
            apiClient.getApiService().writeReviews(userId,trainerId,ratingBar,review)
                .enqueue(object : Callback<BaseResponse> {

                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        val signupResponse = response.body()
                        utilities.hideProgressDialog()

                        if (signupResponse!!.status == true) {
                            val intent = Intent(this@WriteReviewActivity,InstructorDetailActivity::class.java)
                            intent.putExtra("id",trainerId)
                            startActivity(intent)
                            finish()
                        } else {
                            utilities.hideProgressDialog()
                            utilities.showFailureToast(
                                this@WriteReviewActivity,
                                signupResponse.message,
                            )
                        }


                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        utilities.hideProgressDialog()
                        utilities.showFailureToast(this@WriteReviewActivity, t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(
                this@WriteReviewActivity,
                resources.getString(R.string.checkinternet)
            )

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@WriteReviewActivity,InstructorDetailActivity::class.java)
        intent.putExtra("id",trainerId)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        utilities = Utilities(this@WriteReviewActivity)
        utilities.setGrayBar(this@WriteReviewActivity)
        val intent = intent
        trainerId = intent.getStringExtra("id").toString()
        val gsonn = Gson()
        val jsonn: String =
            utilities.getString(this@WriteReviewActivity, "loginResponse").toString()
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        userId = java.lang.String.valueOf(obj.id)
    }
}