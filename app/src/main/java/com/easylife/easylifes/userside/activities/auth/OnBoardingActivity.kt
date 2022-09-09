package com.easylife.easylifes.userside.activities.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.adapter.SplashAdapter
import com.easylife.easylifes.databinding.ActivityOnBoardingBinding
import com.easylife.easylifes.utils.Utilities

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnBoardingBinding
    var images: ArrayList<Int>? = null
    var adpter: SplashAdapter? = null
    private lateinit var utilities : Utilities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()

    }

    private fun initViews() {
        utilities = Utilities(this@OnBoardingActivity)
        utilities.setWhiteBars(this@OnBoardingActivity)
        images = java.util.ArrayList()
        images!!.add(R.drawable.onboard1)
        images!!.add(R.drawable.onboard2)
        images!!.add(R.drawable.onboard3)
        adpter = SplashAdapter(this, images!!)
        binding.viewPager.currentItem = 0
        binding.viewPager.adapter = adpter
        binding.dotsIndicator.setViewPager(binding.viewPager)
        binding.viewPager.clipToPadding = false
        binding.viewPager.clipChildren = false
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
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
                    startActivity(Intent(this@OnBoardingActivity,ReadyToGoActivity::class.java))
                }
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })    }

}