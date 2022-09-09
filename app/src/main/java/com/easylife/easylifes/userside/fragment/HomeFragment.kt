package com.easylife.easylifes.userside.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.notification.NotificationActiivty
import com.easylife.easylifes.userside.adapter.CategoriesAdapter
import com.easylife.easylifes.userside.adapter.HomeSliderAdapter
import com.easylife.easylifes.userside.adapter.SliderApdater
import com.easylife.easylifes.databinding.FragmentHomeBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.SliderItem
import com.easylife.easylifes.model.home.BannersDataModel
import com.easylife.easylifes.model.home.CategoriesDataModel
import com.easylife.easylifes.model.home.HomeResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs


class HomeFragment : Fragment() {
    private lateinit var sliderItemList : ArrayList<SliderItem>
    private lateinit var sliderAdapter : SliderApdater
    private lateinit var sliderHandler : Handler
    private lateinit var sliderRun : Runnable
    var images: ArrayList<Int>? = null
    var adpter: HomeSliderAdapter? = null
    private lateinit var categoryList : ArrayList<JobsDataModel>
    private lateinit var binding : FragmentHomeBinding
    private lateinit var utilities: Utilities
    var profileImage = ""
    var userName = ""
    private lateinit var bannerList: ArrayList<BannersDataModel>
    private lateinit var categoriesList: ArrayList<CategoriesDataModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
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
        if (!jsonn.isEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileImage = obj.profile_image
            userName = obj.name

            Glide.with(this@HomeFragment).load(profileImage).into(binding.imageProfile)
            binding.textName.text = "Hi "+userName

        }
    }

    private fun onClicks() {
        binding.rlNotification.setOnClickListener {
            startActivity(Intent(requireContext(),NotificationActiivty::class.java))
        }
    }



    fun imageSlider(imageList : ArrayList<BannersDataModel>)
    {

        adpter = HomeSliderAdapter(requireContext(), imageList)
        binding.viewPager.currentItem = 0
        binding.viewPager.adapter = adpter
        binding.dotsIndicator.setViewPager(binding.viewPager)
        binding.viewPager.clipToPadding = false
        binding.viewPager.clipChildren = false
        binding.viewPager.offscreenPageLimit = 1

        val nextItemVisiblePx = 30
        val currentItemHorizontalMarginPx = 12
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
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
                }
                if (position == 1) {

                }
                if (position == 2){
                }
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }



    private fun getHomeData() {
        val apiClient: ApiClient = ApiClient()
        if (utilities.isConnectingToInternet(requireContext())) {
            val gsonn = Gson()
            val jsonn: String = utilities.getString(requireContext(), "loginResponse").toString()
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val user_idd: String = java.lang.String.valueOf(obj.id)

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
                            if (signupResponse!!.status == true) {
                                //banner list data
                                bannerList = ArrayList()
                                bannerList = response.body()?.data?.banners!!
                                imageSlider(bannerList)

                                //categories data
                                categoriesList = ArrayList()
                                categoriesList = response.body()!!.data.categories
                                categoryData(categoriesList)



                                //  utilitiess.customToastSuccess(signupResponse.message, context!!)
                            } else {
                                utilities.showFailureToast( requireActivity(),signupResponse.message,)


                            }
                        }

                    }

                    override fun onFailure(call: Call<HomeResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(requireActivity(),t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(requireActivity(), resources.getString(R.string.checkinternet))

        }

    }

    private fun categoryData(categoriesList : ArrayList<CategoriesDataModel>) {
        binding.recyclerViewFitness.layoutManager =LinearLayoutManager(requireContext())
        binding.recyclerViewFitness.adapter = CategoriesAdapter(requireContext(),categoriesList)

    }

}