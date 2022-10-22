package com.easylife.easylifes.userside.activities.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.NotificationAdapter
import com.easylife.easylifes.databinding.ActivityNotificationActiivtyBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.notification.NotificationDataModel
import com.easylife.easylifes.model.notification.NotificationResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActiivty : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationActiivtyBinding
    private lateinit var notificationList: ArrayList<NotificationDataModel>
    private lateinit var utilities: Utilities
    var userId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationActiivtyBinding.inflate(layoutInflater)
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
        utilities = Utilities(this@NotificationActiivty)
        utilities.setGrayBar(this@NotificationActiivty)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@NotificationActiivty, "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = java.lang.String.valueOf(obj.id)
        }

        userNotification()
    }

    private fun userNotification() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@NotificationActiivty)) {

            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "notification-list/"+userId
            apiClient.getApiService().getNotificationList(url)
                .enqueue(object : Callback<NotificationResponseModel> {

                    override fun onResponse(
                        call: Call<NotificationResponseModel>,
                        response: Response<NotificationResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (signupResponse!!.status) {
                            notificationList = ArrayList()
                            notificationList = signupResponse.data
                            binding.rvNotification.layoutManager = LinearLayoutManager(this@NotificationActiivty)
                            binding.rvNotification.adapter = NotificationAdapter(this@NotificationActiivty,notificationList)
                        } else {
                            utilities.showFailureToast(
                                this@NotificationActiivty,
                                signupResponse.message
                            )


                        }


                    }

                    override fun onFailure(call: Call<NotificationResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@NotificationActiivty, t.message!!)
                    }


                })


        } else {

            utilities.showNoInternetToast(this@NotificationActiivty)

        }

    }

}