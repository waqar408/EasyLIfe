package com.easylife.easylifes.trainerside.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.FragmentTrainerHomeBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.home.BannersDataModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.trainerhome.TrainerHomeResponseModel
import com.easylife.easylifes.model.trainerhome.TrainerUserDataModel
import com.easylife.easylifes.trainerside.adapter.TrainerClientsAdapter
import com.easylife.easylifes.userside.activities.notification.NotificationActiivty
import com.easylife.easylifes.userside.adapter.HomeSliderAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class TrainerHomeFragment : Fragment() {
    private lateinit var binding : FragmentTrainerHomeBinding
    private var adpter: HomeSliderAdapter? = null
    private lateinit var utilities: Utilities
    private var categoriesList: ArrayList<TrainerUserDataModel> = ArrayList()
    private lateinit var bannerList: ArrayList<BannersDataModel>
    var profileImage = ""
    var userName = ""
    var userId = ""
    private var firebaseToken = ""
    lateinit var adapter : TrainerClientsAdapter
    var filterList: ArrayList<TrainerUserDataModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTrainerHomeBinding.inflate(inflater,container,false)

        onClicks()
        initViews()

        return binding.root
    }
    private fun initViews() {
        utilities = Utilities(requireContext())
        utilities.setGrayBar(requireActivity())
        adapter = TrainerClientsAdapter(requireContext(),categoriesList)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(requireContext(), "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileImage = obj.profile_image
            userName = obj.name
            userId = obj.id.toString()
            Glide.with(this@TrainerHomeFragment).load(profileImage).into(binding.imageProfile)
            binding.textName.text = "Hi $userName"

        }
        getHomeData()

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filterList.clear()
                if (s.toString().isEmpty()) {
                    binding.recyclerViewFitness.adapter =(TrainerClientsAdapter(requireContext(), categoriesList))
                    adapter.notifyDataSetChanged()
                } else {
                    try {
                        filter(s.toString())
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(requireContext(), "" + e.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })

        //firebase token to send and recieve notification
        val sharedPref = context?.getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        firebaseToken = sharedPref?.getString("FCM_TOKEN", "").toString()
        Log.d("tokennnnnnns", firebaseToken)

        updateFcm(userId, firebaseToken)

        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            binding.tvTime.text = "Good Morning"
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            binding.tvTime.text = "Good Afternoon"
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            binding.tvTime.text = "Good Evening"
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            binding.tvTime.text = "Good Night"
        }
    }
    private fun filter(text: String) {
        if (categoriesList.size > 0) {
            for (post in categoriesList) {
                if (post.name.lowercase()
                        .contains(text.lowercase(Locale.getDefault()))
                ) {
                    filterList.add(post)
                }
            }
            binding.recyclerViewFitness.adapter =(TrainerClientsAdapter(requireContext(), filterList))
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(
                requireContext(),
                "No User Found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onClicks() {
        binding.rlNotification.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationActiivty::class.java))
        }
        binding.rlNotification.setOnClickListener {
            startActivity(Intent(requireContext(),NotificationActiivty::class.java))
        }
    }



    fun imageSlider(imageList : ArrayList<BannersDataModel>)
    {

        adpter = HomeSliderAdapter(requireContext(), imageList)
        binding.viewPager.currentItem = 0
        binding.viewPager.adapter = adpter
        binding.dotsIndicator.attachTo(binding.viewPager)
        binding.viewPager.clipToPadding = false
        binding.viewPager.clipChildren = false
        binding.viewPager.offscreenPageLimit = 1

        val nextItemVisiblePx = 30
        val currentItemHorizontalMarginPx = 12
        nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager.PageTransformer { page: View, position: Float ->
            /*page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))*/
            val r : Float = 1- abs(position)
            page.scaleY = 0.75f +r*0.25f
        }

        binding.viewPager.setPageTransformer(false,pageTransformer)


        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 0) {
                    // viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    Log.d("postion",position.toString())
                }
                if (position == 1) {
                    Log.d("postion",position.toString())
                }
                if (position == 2){
                    Log.d("postion",position.toString())
                }
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun getHomeData() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(requireContext())) {

            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "trainer-home/"+userId
            apiClient.getApiService().trainerHomeData(url)
                .enqueue(object : Callback<TrainerHomeResponseModel> {

                    override fun onResponse(
                        call: Call<TrainerHomeResponseModel>,
                        response: Response<TrainerHomeResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (this@TrainerHomeFragment.isAdded) {
                            if (signupResponse!!.status) {
                                //banner list data
                                bannerList = ArrayList()
                                bannerList = response.body()?.data?.banners!!
                                imageSlider(bannerList)

                                //categories data
                                categoriesList = ArrayList()
                                categoriesList = response.body()!!.data.users
                                categoryData(categoriesList)



                                //  utilitiess.customToastSuccess(signupResponse.message, context!!)
                            } else {
                                utilities.showFailureToast( requireActivity(),signupResponse.message)


                            }
                        }

                    }

                    override fun onFailure(call: Call<TrainerHomeResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(requireActivity(),t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(requireActivity(), resources.getString(R.string.checkinternet))

        }

    }

    private fun categoryData(categoriesList : ArrayList<TrainerUserDataModel>) {
        binding.recyclerViewFitness.layoutManager =LinearLayoutManager(requireContext())
        binding.recyclerViewFitness.adapter = TrainerClientsAdapter(requireContext(),categoriesList)

    }

    private fun updateFcm(
        userId: String,
        token: String
    ) {
        if (utilities.isConnectingToInternet(requireContext())) {
            val apiClient = ApiClient()
            apiClient.getApiService().updateFcmToken(
                userId, "android", token
            )
                .enqueue(object : Callback<BaseResponse> {

                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        val signupResponse = response.body()
                        if (response.isSuccessful) {
                            if (signupResponse?.status!!) {
//                                homeData()
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

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        // Error logging in
                        utilities.hideProgressDialog()
                        //   utilities.showFailureToast(requireActivity(), t.message)

                    }

                })
        } else {
            utilities.showNoInternetToast(requireActivity())
        }
    }
}