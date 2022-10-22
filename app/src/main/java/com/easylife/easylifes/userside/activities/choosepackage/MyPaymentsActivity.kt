package com.easylife.easylifes.userside.activities.choosepackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityMyPaymentsBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.subscribedtrainer.SubscribedTrainerDataModel
import com.easylife.easylifes.model.subscribedtrainer.SubscribedTrainerResponseModel
import com.easylife.easylifes.userside.adapter.SubscribedTrainersAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPaymentsActivity : AppCompatActivity(),SubscribedTrainersAdapter.onAllClientDetailClick {
    private lateinit var binding : ActivityMyPaymentsBinding
    private lateinit var utilities : Utilities
    private lateinit var subscribedTrainerList : ArrayList<SubscribedTrainerDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMyPaymentsBinding.inflate(layoutInflater)
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
        utilities = Utilities(this@MyPaymentsActivity)
        utilities.setGrayBar(this@MyPaymentsActivity)

        getAllSubSribedTrainers()
    }

    private fun getAllSubSribedTrainers() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@MyPaymentsActivity)) {
            val gsonn = Gson()
            val jsonn: String = utilities.getString(this@MyPaymentsActivity, "loginResponse")
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val useridd: String = java.lang.String.valueOf(obj.id)

            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "subscribed-trainers/" + useridd
            apiClient.getApiService().subscribedTrainer(url)
                .enqueue(object : Callback<SubscribedTrainerResponseModel> {

                    override fun onResponse(
                        call: Call<SubscribedTrainerResponseModel>,
                        response: Response<SubscribedTrainerResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()

                        if (signupResponse!!.status) {
                            //banner list data


                            //categories data
                            subscribedTrainerList = ArrayList()
                            subscribedTrainerList = response.body()!!.data
                            allClients(subscribedTrainerList)


                        } else {
                            utilities.showFailureToast(this@MyPaymentsActivity, signupResponse.message)


                        }


                    }

                    override fun onFailure(call: Call<SubscribedTrainerResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@MyPaymentsActivity, t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(
                this@MyPaymentsActivity,
                resources.getString(R.string.checkinternet)
            )

        }

    }

    private fun allClients(subscribedTrainerList: ArrayList<SubscribedTrainerDataModel>) {
        binding.rvSubscribedTrainers.layoutManager = LinearLayoutManager(this@MyPaymentsActivity)
        binding.rvSubscribedTrainers.adapter = SubscribedTrainersAdapter(this@MyPaymentsActivity, subscribedTrainerList, this@MyPaymentsActivity)

    }

    override fun onClickArea(position: Int) {

    }
}