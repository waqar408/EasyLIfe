package com.easylife.easylifes.userside.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.HomeSliderAdapter
import com.easylife.easylifes.userside.adapter.MyPlansAdapter
import com.easylife.easylifes.userside.adapter.TopTrainerAdapter
import com.easylife.easylifes.databinding.FragmentExcersiceBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.home.BannersDataModel
import kotlin.math.abs


class ExcersiceFragment : Fragment() {
    private lateinit var binding : FragmentExcersiceBinding
    var images: ArrayList<Int>? = null
    var adpter: HomeSliderAdapter? = null
    private lateinit var plansList : ArrayList<JobsDataModel>
    private lateinit var topTrainerList : ArrayList<JobsDataModel>
    private lateinit var bannerList: ArrayList<BannersDataModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExcersiceBinding.inflate(inflater,container,false)
        viewPagerCode()
        plansData()
        topTrainerData()
        return binding.root
    }

    private fun topTrainerData() {
        topTrainerList = ArrayList()
        topTrainerList.add(JobsDataModel(R.drawable.trainerimg,""))
        topTrainerList.add(JobsDataModel(R.drawable.trainerimg,""))
        topTrainerList.add(JobsDataModel(R.drawable.trainerimg,""))
        topTrainerList.add(JobsDataModel(R.drawable.trainerimg,""))
        topTrainerList.add(JobsDataModel(R.drawable.trainerimg,""))
        topTrainerList.add(JobsDataModel(R.drawable.trainerimg,""))
        topTrainerList.add(JobsDataModel(R.drawable.trainerimg,""))
        binding.rvTopInstructor.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvTopInstructor.adapter = TopTrainerAdapter(requireContext(),topTrainerList)
    }

    private fun plansData() {
        plansList = ArrayList()
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon,"Nutrition"))
        binding.rvPlans.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvPlans.adapter = MyPlansAdapter(requireContext(),plansList)
    }

    fun viewPagerCode()
    {
        bannerList = ArrayList()
        bannerList.add(BannersDataModel(1,""))
        bannerList.add(BannersDataModel(1,""))
        bannerList.add(BannersDataModel(1,""))
        adpter = HomeSliderAdapter(requireContext(), bannerList)
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


}