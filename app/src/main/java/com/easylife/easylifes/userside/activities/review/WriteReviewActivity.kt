package com.easylife.easylifes.userside.activities.review

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityWriteReviewBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.userside.activities.instructor.InstructorDetailActivity
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
    private var ratingBar = ""
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
            if (ratingBar == "0.0")
            {
                utilities.showFailureToast(this@WriteReviewActivity,"Please Give Rating...")
            }else if (review == ""){
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
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@WriteReviewActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().writeReviews(userId,trainerId,ratingBar,review)
                .enqueue(object : Callback<BaseResponse> {

                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        val signupResponse = response.body()
                        binding.dotloader.visibility = View.GONE

                        if (signupResponse!!.status) {
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
                        binding.dotloader.visibility = View.GONE
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
        trainerId = intent.getStringExtra("trainerid").toString()
        val gsonn = Gson()
        val jsonn: String =
            utilities.getString(this@WriteReviewActivity, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        userId = java.lang.String.valueOf(obj.id.toString())
    }
}