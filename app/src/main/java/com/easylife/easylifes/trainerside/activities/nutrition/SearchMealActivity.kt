package com.easylife.easylifes.trainerside.activities.nutrition

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.ActivitySearchMealBinding
import com.easylife.easylifes.model.search.SearchDataModel
import com.easylife.easylifes.model.search.SearchResponseModel
import com.easylife.easylifes.trainerside.adapter.SearchMealAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchMealActivity : AppCompatActivity(), SearchMealAdapter.onMealTimeClick {
    private lateinit var binding: ActivitySearchMealBinding
    private lateinit var utilities: Utilities
    val apiClient = ApiClient()
    var planid = ""
    var mealtimeid = ""
    var nutritionName = ""
    var clientid = ""
    lateinit var listSearchMeal: ArrayList<SearchDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                //avoid triggering event when text is empty
                if (s.length >= 1) {
                    searchMeal(s.toString())
                }
            }
        })


    }

    private fun searchMeal(
        keyword: String
    ) {
        if (utilities.isConnectingToInternet(this@SearchMealActivity)) {
            apiClient.getApiService().searchMeal(keyword)
                .enqueue(object : Callback<SearchResponseModel> {

                    override fun onResponse(
                        call: Call<SearchResponseModel>,
                        response: Response<SearchResponseModel>
                    ) {
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!.equals(true)) {
                                listSearchMeal = signupResponse.data
                                if (listSearchMeal.isEmpty()) {
                                    binding.lnNoMeal.visibility = View.VISIBLE
                                    binding.rvMealsList.visibility = View.GONE
                                } else {
                                    binding.lnNoMeal.visibility = View.GONE
                                    binding.rvMealsList.visibility = View.VISIBLE
                                }
                                binding.rvMealsList.layoutManager =
                                    LinearLayoutManager(this@SearchMealActivity)
                                binding.rvMealsList.adapter = SearchMealAdapter(
                                    this@SearchMealActivity,
                                    listSearchMeal,
                                    this@SearchMealActivity
                                )
                            } else {
                                utilities.showFailureToast(
                                    this@SearchMealActivity,
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@SearchMealActivity,
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SearchResponseModel>, t: Throwable) {
                        // Error logging in
                        utilities.showFailureToast(this@SearchMealActivity, t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(this@SearchMealActivity)
        }
    }

    private fun initViews() {
        utilities = Utilities(this@SearchMealActivity)
        utilities.setGrayBar(this@SearchMealActivity)
        planid = intent.getStringExtra("mealplanid").toString()
        mealtimeid = intent.getStringExtra("mealtimeid").toString()
        clientid = intent.getStringExtra("clientid").toString()
        nutritionName = intent.getStringExtra("mealname").toString()
        binding.tvName.text = nutritionName

        binding.layoutBackArrow.setOnClickListener {
            val intent = Intent(this@SearchMealActivity, ClientNutritionActivity::class.java)
            intent.putExtra("clientid", clientid)
            intent.putExtra("mealplanid",planid)
            startActivity(intent)
            finish()

        }
    }

    override fun onMealTimeClick(position: Int) {
        val model = listSearchMeal[position]
        val gson = Gson()
        val mySelectMeal = gson.toJson(model)
        val intent = Intent(this@SearchMealActivity, NutritionSelectedActivity::class.java)
        intent.putExtra("selectedMeal", mySelectMeal)
        intent.putExtra("mealtimeid", mealtimeid.toString())
        intent.putExtra("mealplanid", planid.toString())
        intent.putExtra("nutritionName", nutritionName)
        intent.putExtra("clientid", clientid)
        startActivity(intent)
        finish()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@SearchMealActivity, ClientNutritionActivity::class.java)
        intent.putExtra("clientid", clientid)
        intent.putExtra("mealplanid",planid)
        startActivity(intent)
        finish()
    }
}