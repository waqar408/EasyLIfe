package com.easylife.easylifes.userside.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.FragmentHomeBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.home.BannersDataModel
import com.easylife.easylifes.model.home.CategoriesDataModel
import com.easylife.easylifes.model.home.HomeResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.userside.activities.notification.NotificationActiivty
import com.easylife.easylifes.userside.activities.profile.UserProfileActivity
import com.easylife.easylifes.userside.adapter.CategoriesAdapter
import com.easylife.easylifes.userside.adapter.HomeSliderAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.math.abs


class HomeFragment : Fragment(),HomeSliderAdapter.onPageListner {

    private var adpter: HomeSliderAdapter? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var utilities: Utilities
    var profileImage = ""
    var userName = ""
    var userId = ""
    var positions = ""
    private var firebaseToken = ""
    private lateinit var bannerList: ArrayList<BannersDataModel>
    private lateinit var categoriesList: ArrayList<CategoriesDataModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        /*sliderItems()
        itemsSliderView()*/
        initViews()
        onClicks()
        getHomeData()
        return binding.root
    }

    private fun initViews() {
        utilities = Utilities(requireContext())
        utilities.setGrayBar(requireActivity())
        val gsonn = Gson()
        val jsonn: String = utilities.getString(requireContext(), "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileImage = obj.profile_image
            userName = obj.name
            userId = obj.id.toString()

            Glide.with(this@HomeFragment).load(profileImage).into(binding.imageProfile)
            binding.textName.text = "Hi $userName"

        }
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

    private fun onClicks() {
        binding.rlNotification.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationActiivty::class.java))
        }
        binding.layoutProfileName.setOnClickListener {
            val intent = Intent(requireContext(), UserProfileActivity::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }
    }


    fun imageSlider(imageList: ArrayList<BannersDataModel>) {
        adpter = HomeSliderAdapter(requireContext(), imageList,this@HomeFragment)
        binding.viewPager.currentItem = 0
        binding.viewPager.adapter = adpter
        binding.dotsIndicator.attachTo(binding.viewPager)
        binding.viewPager.clipToPadding = false
        binding.viewPager.clipChildren = false
        binding.viewPager.offscreenPageLimit = 1

        val pageTransformer = ViewPager.PageTransformer { page: View, position: Float ->
            /*page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))*/
            val r: Float = 1 - abs(position)
            page.scaleY = 0.75f + r * 0.25f
        }

        binding.viewPager.setPageTransformer(false, pageTransformer)


        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                /*if (position == 0) {
                    // viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                if (position == 1) {

                }
                if (position == 2){
                }*/
                positions = position.toString()
            }

            override fun onPageSelected(position: Int) {
                positions = position.toString()

            }


            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }


    private fun getHomeData() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(requireContext())) {

            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "user-home"
            apiClient.getApiService().homeData(url)
                .enqueue(object : Callback<HomeResponseModel> {

                    override fun onResponse(
                        call: Call<HomeResponseModel>,
                        response: Response<HomeResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (this@HomeFragment.isAdded) {
                            if (signupResponse!!.status) {
                                //banner list data
                                bannerList = ArrayList()
                                bannerList = response.body()?.data?.banners!!
                                imageSlider(bannerList)

                                //categories data
                                categoriesList = ArrayList()
                                categoriesList = response.body()!!.data.categories
                                categoryData(categoriesList)


                            } else {
                                utilities.showFailureToast(
                                    requireActivity(),
                                    signupResponse.message
                                )


                            }
                        }

                    }

                    override fun onFailure(call: Call<HomeResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(requireActivity(), t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(
                requireActivity(),
                resources.getString(R.string.checkinternet)
            )

        }

    }

    private fun categoryData(categoriesList: ArrayList<CategoriesDataModel>) {
        binding.recyclerViewFitness.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFitness.adapter = CategoriesAdapter(requireContext(), categoriesList)

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

    override fun onPageClick(position: Int) {
        val viewIntent = Intent(
            "android.intent.action.VIEW",
            Uri.parse(bannerList[position].website_url)
        )
        startActivity(viewIntent)
    }

}