package com.easylife.easylifes.userside.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.GoalsAdapter
import com.easylife.easylifes.databinding.FragmentGoalsBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.search.SearchDataModel
import com.easylife.easylifes.model.search.SearchResponseModel
import com.easylife.easylifes.trainerside.activities.nutrition.ClientNutritionActivity
import com.easylife.easylifes.trainerside.activities.nutrition.NutritionSelectedActivity
import com.easylife.easylifes.trainerside.adapter.SearchMealAdapter
import com.easylife.easylifes.userside.activities.clientnutrition.SearchNutritionDetailActivity
import com.easylife.easylifes.userside.adapter.SearchMealUserAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GoalsFragment : Fragment(),SearchMealUserAdapter.onMealTimeClick {
    private lateinit var binding : FragmentGoalsBinding
    private lateinit var utilities: Utilities
    val apiClient = ApiClient()
    private var planid = ""
    private var mealtimeid = ""
    private var nutritionName = ""
    var clientid = ""
    lateinit var listSearchMeal: ArrayList<SearchDataModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGoalsBinding.inflate(inflater,container,false)

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
                if (s.isNotEmpty()) {
                    searchMeal(s.toString())
                }
            }
        })
        return binding.root
    }

    private fun searchMeal(
        keyword: String
    ) {
        if (utilities.isConnectingToInternet(requireContext())) {
            apiClient.getApiService().searchMeal(keyword)
                .enqueue(object : Callback<SearchResponseModel> {

                    override fun onResponse(
                        call: Call<SearchResponseModel>,
                        response: Response<SearchResponseModel>
                    ) {
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
                                listSearchMeal = signupResponse.data
                                if (listSearchMeal.isEmpty()) {
                                    binding.lnNoMeal.visibility = View.VISIBLE
                                    binding.rvMealsList.visibility = View.GONE
                                } else {
                                    binding.lnNoMeal.visibility = View.GONE
                                    binding.rvMealsList.visibility = View.VISIBLE
                                }
                                binding.rvMealsList.layoutManager =
                                    LinearLayoutManager(requireContext())
                                binding.rvMealsList.adapter = SearchMealUserAdapter(
                                    requireContext(),
                                    listSearchMeal,
                                    this@GoalsFragment
                                )
                            } else {
                                utilities.showFailureToast(
                                    requireActivity(),
                                    signupResponse.message
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                requireActivity(),
                                signupResponse!!.message
                            )
                        }

                    }

                    override fun onFailure(call: Call<SearchResponseModel>, t: Throwable) {
                        // Error logging in
                        utilities.showFailureToast(requireActivity(), t.message)
                    }
                })
        } else {
            utilities.showNoInternetToast(requireActivity())
        }
    }

    private fun initViews() {
        utilities = Utilities(requireContext())
        utilities.setGrayBar(requireActivity())


    }

    override fun onMealTimeClick(position: Int) {
        val model = listSearchMeal[position]
        val gson = Gson()
        val mySelectMeal = gson.toJson(model)
        val intent = Intent(requireContext(), SearchNutritionDetailActivity::class.java)
        intent.putExtra("selectedMeal", mySelectMeal)
        intent.putExtra("mealtimeid", mealtimeid)
        intent.putExtra("mealplanid", planid)
        intent.putExtra("nutritionName", nutritionName)
        intent.putExtra("clientid", clientid)
        startActivity(intent)

    }


}