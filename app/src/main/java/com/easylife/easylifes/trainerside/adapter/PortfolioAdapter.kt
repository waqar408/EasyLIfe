package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.easylife.easylifes.R
import com.easylife.easylifes.model.trainerportfolio.PortfolioDataModel
import com.google.android.material.imageview.ShapeableImageView


class PortfolioAdapter(
    val context: Context,
    val list: ArrayList<PortfolioDataModel>,
    var mListener: onAllWorkoutClick,
    var mListner : onDeleteClick

) :
    RecyclerView.Adapter<PortfolioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_videos, parent, false)
        return ViewHolder(view,mListener,mListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: PortfolioDataModel = list.get(position)
        val requestOptions = RequestOptions()
        Glide.with(context)
            .load(model.video)
            .apply(requestOptions)
            .thumbnail(Glide.with(context).load(model.video))
            .into(holder.imgPic)
        holder.itemView.setOnClickListener {
            mListener.onClickArea(position)
        }
        holder.rlDelete.setOnClickListener {
            mListner.onDeleteClick(position)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onAllWorkoutClick,listener2: onDeleteClick) :
        RecyclerView.ViewHolder(itemView) {
        val imgPic: ShapeableImageView = itemView.findViewById(R.id.imgPic);
        val rlDelete: RelativeLayout = itemView.findViewById(R.id.rlDelete);



    }

    interface onAllWorkoutClick {
        fun onClickArea(position: Int)
    }

    interface onDeleteClick {
        fun onDeleteClick(position: Int)
    }




}