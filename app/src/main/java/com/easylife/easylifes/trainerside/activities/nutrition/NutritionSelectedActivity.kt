package com.easylife.easylifes.trainerside.activities.nutrition

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityNutritionSelectedBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.search.SearchDataModel
import com.easylife.easylifes.trainerside.activities.clientdetail.AllWorkoutsActivity
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Response


class NutritionSelectedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNutritionSelectedBinding
    private lateinit var utilities: Utilities
    private lateinit var searchDataModel: SearchDataModel
    var servingSize = ""
    var servingId = ""
    var noOfServings = ""
    var planid = ""
    var mealtimeid = ""
    var mealid = ""
    var nutritionName = ""
    var clientid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionSelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
    }

    private fun onClicks() {

        binding.layoutBackArrow.setOnClickListener {
            val intent = Intent(this@NutritionSelectedActivity, SearchMealActivity::class.java)
            intent.putExtra("mealtimeid",mealtimeid.toString())
            intent.putExtra("mealplanid",planid.toString())
            intent.putExtra("mealname",nutritionName)
            intent.putExtra("clientid",clientid)
            startActivity(intent)
            finish()
        }
        binding.layoutCreateWorkout.setOnClickListener {
            noOfServings = binding.edNoOfServing.text.toString()
            if (noOfServings.equals("")) {
                utilities.showFailureToast(
                    this@NutritionSelectedActivity,
                    "Please Enter No Of Servings"
                )
            } else {
                createMeal(noOfServings)
            }
        }
    }


    fun createMeal(noOfServings : String) {

        val apiClient = ApiClient()
        val array1 = JsonArray()
        val array2 = JsonArray()
        val jsonObject = JsonObject()
        val jsonObject2 = JsonObject()
        val jsonObject3 = JsonObject()
        jsonObject.addProperty("meal_plan_id", planid)
        jsonObject2.addProperty("meal_time_id", mealtimeid)
        jsonObject3.addProperty("meal_id", mealid)
        jsonObject3.addProperty("serving_id", servingId)
        jsonObject3.addProperty("serving_quantity", noOfServings)
        array2.add(jsonObject3)
        jsonObject2.add("foods", array2)
        array1.add(jsonObject2)
        jsonObject.add("meal_foods", array1)

        Log.d("jsonjsonjson", jsonObject.toString())
        binding.dotloader.visibility = View.VISIBLE
        apiClient.getApiService().addFood(jsonObject)
            .enqueue(object : retrofit2.Callback<BaseResponse?> {
                override fun onResponse(
                    call: Call<BaseResponse?>,
                    response: Response<BaseResponse?>
                ) {
                    binding.dotloader.visibility = View.GONE
                    if (response.body() != null) {
                        val status = response.body()!!.status
                        utilities.showSuccessToast(this@NutritionSelectedActivity,response.message())
                        if (status == true) {
                            Handler(Looper.myLooper()!!).postDelayed({
                                val intent = Intent(this@NutritionSelectedActivity, ClientNutritionActivity::class.java)
                                intent.putExtra("mealplanid", planid)
                                intent.putExtra("clientid",clientid)
                                startActivity(intent)
                                finish()
                            },1000)

                        } else {
                            utilities.showFailureToast(
                                this@NutritionSelectedActivity,
                                response.body()!!.message
                            )
                        }
                    }

                }

                override fun onFailure(
                    call: Call<BaseResponse?>,
                    t: Throwable
                ) {
                    binding.dotloader.visibility = View.GONE
                    t.printStackTrace()
                    // showToast(t.message.toString())

                }
            })


    }


    private fun initViews() {
        utilities = Utilities(this@NutritionSelectedActivity)
        utilities.setGrayBar(this@NutritionSelectedActivity)
        val intent = intent
        planid = intent.getStringExtra("mealplanid").toString()
        mealtimeid = intent.getStringExtra("mealtimeid").toString()
        clientid = intent.getStringExtra("clientid").toString()
        nutritionName = intent.getStringExtra("nutritionName").toString()
        binding.tvNames.text = nutritionName
        val gson = Gson()
        searchDataModel =
            gson.fromJson(intent.getStringExtra("selectedMeal"), SearchDataModel::class.java)
        mealid = searchDataModel.id.toString()
        binding.tvName.text = searchDataModel.meal_title
        binding.tvCalories.text = searchDataModel.meal_calories
        binding.tvProtein.text = searchDataModel.meal_protien
        binding.tvCarbs.text = searchDataModel.meal_carbs
        binding.tvFat.text = searchDataModel.meal_fat
        binding.tvFiber.text = searchDataModel.meal_fibre
        binding.tvSodium.text = searchDataModel.meal_sodium
        binding.tvSugar.text = searchDataModel.meal_sugar
        val item = java.util.ArrayList<String>()
        for (i in 0..searchDataModel.servings.size - 1) {
            item.add(searchDataModel.servings.get(i).serving_size)
        }
        if (item != null) {
            val adapter = ArrayAdapter(this@NutritionSelectedActivity, R.layout.spinner_text, item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.setAdapter(adapter)
        }

        binding.spinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                servingSize = searchDataModel.servings.get(position).serving_size
                servingId = searchDataModel.servings.get(position).id.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@NutritionSelectedActivity, SearchMealActivity::class.java)
        intent.putExtra("mealtimeid",mealtimeid.toString())
        intent.putExtra("mealplanid",planid.toString())
        intent.putExtra("mealname",nutritionName)
        intent.putExtra("clientid",clientid)
        startActivity(intent)
        finish()
    }
}