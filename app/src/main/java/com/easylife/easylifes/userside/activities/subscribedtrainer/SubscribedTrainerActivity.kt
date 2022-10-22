package com.easylife.easylifes.userside.activities.subscribedtrainer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivitySubscribedTrainerBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.subscribedtrainer.SubscribedTrainerDataModel
import com.easylife.easylifes.model.subscribedtrainer.SubscribedTrainerResponseModel
import com.easylife.easylifes.userside.activities.workout.WorkoutActivity
import com.easylife.easylifes.userside.adapter.SubscribedTrainerAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SubscribedTrainerActivity : AppCompatActivity(),
    SubscribedTrainerAdapter.onAllClientDetailClick {
    private lateinit var binding: ActivitySubscribedTrainerBinding
    private lateinit var utilities: Utilities
    lateinit var adapter: SubscribedTrainerAdapter
    private var subscribedTrainerList: ArrayList<SubscribedTrainerDataModel> = ArrayList()
    var filterList: ArrayList<SubscribedTrainerDataModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscribedTrainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SubscribedTrainerAdapter(this@SubscribedTrainerActivity, subscribedTrainerList, this)
        initViews()
    }

    private fun initViews() {
        utilities = Utilities(this@SubscribedTrainerActivity)
        utilities.setGrayBar(this@SubscribedTrainerActivity)
        getAllClients()

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filterList.clear()
                if (s.toString().isEmpty()) {
                    binding.rvAllClients.adapter = (SubscribedTrainerAdapter(
                        this@SubscribedTrainerActivity,
                        subscribedTrainerList,
                        this@SubscribedTrainerActivity
                    ))
                    adapter.notifyDataSetChanged()
                } else {
                    try {
                        filter(s.toString())
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(
                            this@SubscribedTrainerActivity,
                            "" + e.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        })

        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
    }

    private fun filter(text: String) {
        if (subscribedTrainerList.size > 0) {
            for (post in subscribedTrainerList) {
                if (post.name.lowercase().contains(text.lowercase(Locale.getDefault()))
                ) {
                    filterList.add(post)
                }
            }
            binding.rvAllClients.adapter = (SubscribedTrainerAdapter(
                this@SubscribedTrainerActivity,
                filterList,
                this@SubscribedTrainerActivity
            ))
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(
                this@SubscribedTrainerActivity,
                "No Trainer Found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getAllClients() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@SubscribedTrainerActivity)) {
            val gsonn = Gson()
            val jsonn: String = utilities.getString(this@SubscribedTrainerActivity, "loginResponse")
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
                            utilities.showFailureToast(this@SubscribedTrainerActivity, signupResponse.message)


                        }


                    }

                    override fun onFailure(call: Call<SubscribedTrainerResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@SubscribedTrainerActivity, t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(
                this@SubscribedTrainerActivity,
                resources.getString(R.string.checkinternet)
            )

        }

    }

    private fun allClients(subscribedTrainerList: ArrayList<SubscribedTrainerDataModel>) {
        binding.rvAllClients.layoutManager = LinearLayoutManager(this@SubscribedTrainerActivity)
        binding.rvAllClients.adapter = SubscribedTrainerAdapter(this@SubscribedTrainerActivity, subscribedTrainerList, this@SubscribedTrainerActivity)

    }

    override fun onClickArea(position: Int) {
        val model = subscribedTrainerList[position]
        val intent = Intent(this@SubscribedTrainerActivity, WorkoutActivity::class.java)
        intent.putExtra("clientid", model.id.toString())
        startActivity(intent)

    }
}