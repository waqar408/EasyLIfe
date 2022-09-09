package com.easylife.easylifes.userside.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.easylife.easylifes.R
import com.easylife.easylifes.model.SliderItem

class SliderApdater(
    val viewpager : ViewPager2,
    val imageList : ArrayList<SliderItem>):RecyclerView.Adapter<SliderApdater.SliderViewHolder>() {
    inner class SliderViewHolder(var v : View): RecyclerView.ViewHolder(v){
        val img = v.findViewById<ImageView>(R.id.imageSlider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.slider_items,parent,false)
        return SliderViewHolder(v)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
       val listImg = imageList[position]
        holder.img.setImageResource(listImg.img)
        if (position == imageList.size -2)
        {
            viewpager.post(run)
        }
    }
    val run = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = imageList.size
}