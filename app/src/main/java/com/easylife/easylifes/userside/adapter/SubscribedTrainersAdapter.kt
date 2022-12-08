package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.subscribedtrainer.SubscribedTrainerDataModel
import com.google.android.material.imageview.ShapeableImageView


class SubscribedTrainersAdapter(
    val context: Context,
    val list: ArrayList<SubscribedTrainerDataModel>,
    var mListener: onAllClientDetailClick

) :
    RecyclerView.Adapter<SubscribedTrainersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_mypayments, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: SubscribedTrainerDataModel = list[position]
        val pacakgePrice = model.package_price
        val packageCurrency = model.price_currency
        Glide.with(context).load(model.profile_image).into(holder.imgProfile)
        holder.tvPackagePrice.text = "$pacakgePrice $packageCurrency"
        val isExpired = model.is_expired
        if (isExpired)
        {
            holder.imgClose.visibility = View.VISIBLE
            holder.tvDuration.text ="Subscription End"
        }else{
            holder.imgTick.visibility = View.VISIBLE
            holder.tvDuration.text = model.validity_days+" Days"
        }

        holder.tvTrainername.text = model.name
        holder.tvPackageType.text = model.package_name


    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onAllClientDetailClick) :
        RecyclerView.ViewHolder(itemView) {
        val imgProfile: ShapeableImageView = itemView.findViewById(R.id.imgProfile)
        val tvPackagePrice: TextView = itemView.findViewById(R.id.tvPackagePrice)
        val imgTick: ImageView = itemView.findViewById(R.id.imgTick)
        val imgClose: ImageView = itemView.findViewById(R.id.imgClose)
        val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        val tvTrainername: TextView = itemView.findViewById(R.id.tvTrainername)
        val tvPackageType: TextView = itemView.findViewById(R.id.tvPackageType)

    }

    interface onAllClientDetailClick {
        fun onClickArea(position: Int)
    }




}