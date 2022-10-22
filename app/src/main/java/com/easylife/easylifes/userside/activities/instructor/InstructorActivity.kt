package com.easylife.easylifes.userside.activities.instructor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityInstructorBinding
import com.easylife.easylifes.userside.adapter.InstructorAdapter
import com.easylife.easylifes.model.categorytrainer.CategoryTrainerDataModel
import com.easylife.easylifes.model.categorytrainer.CategoryTrainerResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstructorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInstructorBinding
    private lateinit var categoryTrainerList: ArrayList<CategoryTrainerDataModel>
    private lateinit var utilities: Utilities
    var id = ""
    var user= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()


    }

    private fun initViews() {
        utilities = Utilities(this@InstructorActivity)
        utilities.setGrayBar(this@InstructorActivity)
        val intent = intent
        id = intent.getStringExtra("id").toString()

        getCategoryTrainer()

        binding.layoutBackArrow.setOnClickListener {
            finish()
        }

    }

    private fun getCategoryTrainer() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@InstructorActivity)) {
            val gsonn = Gson()
            val jsonn: String = utilities.getString(this@InstructorActivity, "loginResponse")
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val userId: String = java.lang.String.valueOf(obj.id)

            utilities.showProgressDialog(this@InstructorActivity,"Loading Data...")
            val url = apiClient.BASE_URL + "category-trainers/"+userId+"/"+id
            apiClient.getApiService().categoryTrainer(url)
                .enqueue(object : Callback<CategoryTrainerResponseModel> {

                    override fun onResponse(
                        call: Call<CategoryTrainerResponseModel>,
                        response: Response<CategoryTrainerResponseModel>
                    ) {
                        val signupResponse = response.body()
                        utilities.hideProgressDialog()

                            if (signupResponse!!.status) {

                                categoryTrainerList = ArrayList()
                                categoryTrainerList = signupResponse.data
                                binding.rvTrainerLIst.layoutManager =
                                    LinearLayoutManager(this@InstructorActivity, LinearLayoutManager.VERTICAL, false)
                                binding.rvTrainerLIst.adapter =
                                    InstructorAdapter(this@InstructorActivity, categoryTrainerList)



                                //  utilitiess.customToastSuccess(signupResponse.message, context!!)
                            } else {
                                utilities.hideProgressDialog()
                                utilities.showFailureToast(
                                    this@InstructorActivity,
                                    signupResponse.message
                                )


                            }


                    }

                    override fun onFailure(call: Call<CategoryTrainerResponseModel>, t: Throwable) {
                        utilities.hideProgressDialog()
                        utilities.showFailureToast(this@InstructorActivity,t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(this@InstructorActivity, resources.getString(R.string.checkinternet))

        }

    }

}