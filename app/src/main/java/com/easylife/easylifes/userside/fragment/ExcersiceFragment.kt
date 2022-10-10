package com.easylife.easylifes.userside.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.HomeSliderAdapter
import com.easylife.easylifes.userside.adapter.TopTrainerAdapter
import com.easylife.easylifes.databinding.FragmentExcersiceBinding
import com.easylife.easylifes.model.home.BannersDataModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.subscribedtrainer.SubscribedTrainerDataModel
import com.easylife.easylifes.model.toptrainers.TopTrainersResponseModel
import com.easylife.easylifes.userside.activities.subscribedtrainer.SubscribedTrainerActivity
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs


class ExcersiceFragment : Fragment() {
    private lateinit var binding : FragmentExcersiceBinding
    private var adpter: HomeSliderAdapter? = null
    private lateinit var bannerList: ArrayList<BannersDataModel>
    private lateinit var utilities : com.easylife.easylifes.utils.Utilities
    private lateinit var trainerList : ArrayList<SubscribedTrainerDataModel>
    var profileImage = ""
    var userName = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExcersiceBinding.inflate(inflater,container,false)

        initViews()
        onClicks()
        return binding.root
    }

    private fun initViews() {
        utilities = com.easylife.easylifes.utils.Utilities(requireContext())
        val gsonn = Gson()
        val jsonn: String = utilities.getString(requireContext(), "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            profileImage = obj.profile_image
            userName = obj.name

            Glide.with(requireContext()).load(profileImage).into(binding.imageProfile)
            binding.textName.text = "Hi $userName"

        }
    }

    private fun onClicks() {
        binding.rls.setOnClickListener {
            val intent = Intent(requireContext(),SubscribedTrainerActivity::class.java)
            startActivity(intent)
        }
        getTrainersList()
    }



    fun imageSlider(bannerLists: ArrayList<BannersDataModel>)
    {

        adpter = HomeSliderAdapter(requireContext(),bannerLists)
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
                /*if (position == 0) {
                    // viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                if (position == 1) {

                }
                if (position == 2){
                }*/
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }


    private fun getTrainersList() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(requireContext())) {
            val gsonn = Gson()
            val jsonn: String = utilities.getString(requireContext(), "loginResponse")
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val userId: String = java.lang.String.valueOf(obj.id)

            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "top-trainers/"+userId
            apiClient.getApiService().topTrainerList(url)
                .enqueue(object : Callback<TopTrainersResponseModel> {

                    override fun onResponse(
                        call: Call<TopTrainersResponseModel>,
                        response: Response<TopTrainersResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (this@ExcersiceFragment.isAdded) {
                            if (signupResponse!!.status) {
                                //banner list data
                                bannerList = ArrayList()
                                bannerList = response.body()?.data?.trending_workouts!!
                                imageSlider(bannerList)

                                //categories data
                                trainerList = ArrayList()
                                trainerList = response.body()!!.data.top_trainers
                                categoryData(trainerList)



                            } else {
                                utilities.showFailureToast( requireActivity(),signupResponse.message)


                            }
                        }

                    }

                    override fun onFailure(call: Call<TopTrainersResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(requireActivity(),t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(requireActivity(), resources.getString(R.string.checkinternet))

        }

    }

    private fun categoryData(categoriesList : ArrayList<SubscribedTrainerDataModel>) {
        binding.rvTopInstructor.layoutManager =LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvTopInstructor.adapter = TopTrainerAdapter(requireContext(),categoriesList)

    }


}