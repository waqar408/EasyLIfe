package com.easylife.easylifes.userside.activities.bodymeasurement

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.easylife.easylifes.databinding.ActivityBodyMeasurementsBinding
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BodyMeasurementsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBodyMeasurementsBinding
    private lateinit var utilities: Utilities
    var age = ""
    var weight = ""
    var weightUnit = ""
    var heightUnit = ""
    var height = ""
    private var heightFt =""
    private var heightInch = ""
    private var heightCm = ""
    var userId = ""

    val apiClient = ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyMeasurementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.layoutSend.setOnClickListener {
            val age = binding.edAge.text.toString()
            val weight = binding.edWeight.text.toString()
            val feet = binding.edFeet.text.toString()
            val inch = binding.edInch.text.toString()
            val cm = binding.edCm.text.toString()
            heightCm = if (heightUnit == "cm") {
                cm
            }else{
                "$feet.$inch"
            }
            updateWithoutImageApi(age,weight,heightCm)
        }
    }

    private fun updateWithoutImageApi(
        age: String,
        weight: String,
        height: String
    ) {
        if (utilities.isConnectingToInternet(this@BodyMeasurementsActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().updateProfile(
                userId, "", "",
                "",
                "", "", age, weight,
                weightUnit, height, heightUnit,
                "", "", "",
                "", "", "",
                "", "", "", "", "", "",
                "","","","","","")
                .enqueue(object : Callback<SignupResponseModel> {

                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        val signupResponse = response.body()
                        binding.dotloader.visibility = View.GONE
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                utilities.showSuccessToast(this@BodyMeasurementsActivity, signupResponse.message)
                                val gson = Gson()
                                val json = gson.toJson(signupResponse.data)
                                utilities.saveString(this@BodyMeasurementsActivity, "loginResponse", json)
                            } else {
                                utilities.showFailureToast(
                                    this@BodyMeasurementsActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@BodyMeasurementsActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        // Error logging in
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@BodyMeasurementsActivity, t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(this@BodyMeasurementsActivity)
        }
    }

    private fun initViews() {
        utilities = Utilities(this@BodyMeasurementsActivity)
        utilities.setGrayBar(this@BodyMeasurementsActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@BodyMeasurementsActivity, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        userId = obj.id.toString()
        age = obj.age
        weight = obj.weight
        weightUnit = obj.weight_unit
        height = obj.height
        heightUnit = obj.height_unit
        if (heightUnit == "ft")
        {
            heightFt = height.dropLast(2)
            heightInch = height.drop(2)

        }
        binding.edAge.text = Editable.Factory.getInstance().newEditable(age)
        binding.edWeight.text = Editable.Factory.getInstance().newEditable(weight)
        binding.edFeet.text = Editable.Factory.getInstance().newEditable(heightFt)
        binding.edInch.text = Editable.Factory.getInstance().newEditable(heightInch)
        binding.edCm.text =  Editable.Factory.getInstance().newEditable(height)
        val list1 = arrayOf("kg","lbs")
         val adapter1 =    ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list1)
        binding.spinner.adapter = adapter1
        val spinnerPosition: Int = adapter1.getPosition(weightUnit)
        binding.spinner.setSelection(spinnerPosition)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                weightUnit = list1[p2]


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val list = arrayOf("ft","cm")
        val adapter =    ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        binding.spinnerHeight.adapter = adapter
        val spinnerHeight: Int = adapter.getPosition(heightUnit)
        binding.spinnerHeight.setSelection(spinnerHeight)
        binding.spinnerHeight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                heightUnit = list[p2]
                if(heightUnit == "ft")
                {
                    binding.lnFeet.visibility = View.VISIBLE
                    binding.lnCm.visibility = View.GONE
                }else{
                    binding.lnCm.visibility = View.VISIBLE
                    binding.lnFeet.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

}