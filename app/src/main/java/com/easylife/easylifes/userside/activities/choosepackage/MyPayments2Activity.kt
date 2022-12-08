package com.easylife.easylifes.userside.activities.choosepackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityMyPayments2Binding
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

class MyPayments2Activity : AppCompatActivity() ,SubscribedTrainersAdapter.onAllClientDetailClick{
    private lateinit var binding : ActivityMyPayments2Binding
    private lateinit var utilities : Utilities
    private lateinit var subscribedTrainerList : ArrayList<SubscribedTrainerDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPayments2Binding.inflate(layoutInflater)
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
        utilities = Utilities(this@MyPayments2Activity)
        utilities.setGrayBar(this@MyPayments2Activity)

        getAllSubSribedTrainers()
    }

    private fun getAllSubSribedTrainers() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@MyPayments2Activity)) {
            val gsonn = Gson()
            val jsonn: String = utilities.getString(this@MyPayments2Activity, "loginResponse")
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
                            utilities.showFailureToast(this@MyPayments2Activity, signupResponse.message)


                        }


                    }

                    override fun onFailure(call: Call<SubscribedTrainerResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@MyPayments2Activity, t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(
                this@MyPayments2Activity,
                resources.getString(R.string.checkinternet)
            )

        }

    }

    private fun allClients(subscribedTrainerList: ArrayList<SubscribedTrainerDataModel>) {
        binding.rvSubscribedTrainers.layoutManager = LinearLayoutManager(this@MyPayments2Activity)
        binding.rvSubscribedTrainers.adapter = SubscribedTrainersAdapter(this@MyPayments2Activity, subscribedTrainerList, this@MyPayments2Activity)

    }

    override fun onClickArea(position: Int) {

    }
}
