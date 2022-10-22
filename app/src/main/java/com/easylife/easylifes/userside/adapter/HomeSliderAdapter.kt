package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.home.BannersDataModel


class HomeSliderAdapter(private val context: Context, private val list: ArrayList<BannersDataModel>) :
    PagerAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        // pass object in view
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_homeslider, null)
        val imageView = view.findViewById<ImageView>(R.id.imageview)
        val model = list[position]
        Glide.with(context).asBitmap().load(model.banner_image).into(imageView)
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //remove object from conatiner
        container.removeView(`object` as View)
    }
}