package com.easylife.easylifes.userside.activities.Support

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivitySupportBinding
import com.easylife.easylifes.model.address.AddressResponseModel
import com.easylife.easylifes.model.faq.FaqDataModel
import com.easylife.easylifes.model.faq.FaqResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.userside.adapter.FaqAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupportActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupportBinding
    private lateinit var utilities: Utilities
    lateinit var listFaq : ArrayList<FaqDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()


    }

    private fun initViews() {
        utilities = Utilities(this@SupportActivity)
        utilities.setGrayBar(this@SupportActivity)

        getFaq()
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "easylife4661@gmail.com"))
                intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject")
                intent.putExtra(Intent.EXTRA_TEXT, "your_text")
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                //TODO smth
            }
        }
    }

    private fun getFaq() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@SupportActivity)) {

            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "faqs"
            apiClient.getApiService().getFaq(url)
                .enqueue(object : Callback<FaqResponseModel> {

                    override fun onResponse(
                        call: Call<FaqResponseModel>,
                        response: Response<FaqResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (signupResponse!!.status) {
                            listFaq = ArrayList()
                            listFaq = signupResponse.data
                            binding.rvFaq.layoutManager = LinearLayoutManager(this@SupportActivity)
                            binding.rvFaq.adapter = FaqAdapter(this@SupportActivity,listFaq)
                        } else {
                            utilities.showFailureToast(
                                this@SupportActivity,
                                signupResponse.message
                            )


                        }


                    }

                    override fun onFailure(call: Call<FaqResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@SupportActivity, t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(
                this@SupportActivity,
                resources.getString(R.string.checkinternet)
            )

        }

    }

}