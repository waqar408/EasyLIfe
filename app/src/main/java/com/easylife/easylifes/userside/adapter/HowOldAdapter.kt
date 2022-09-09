package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.model.HowOldModel


class HowOldAdapter(
    val context: Context,
    val list: ArrayList<HowOldModel>,
    var mListener: onClick
) :
    RecyclerView.Adapter<HowOldAdapter.ViewHolder>() {
    var singleItemSelectPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_how_old, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: HowOldModel = list.get(position)
        holder.tvWeight.text = model.number

        if (singleItemSelectPosition == position) {
            holder.tvWeight.setTextColor(context.resources.getColor(R.color.appColor))
            holder.tvWeight.setBackgroundResource(R.drawable.btn_transparentback)
        } else {
            holder.tvWeight.setBackgroundResource(R.color.transparent)
        }
        holder.itemView.setOnClickListener {

            setSingleSelection(holder.adapterPosition)
            mListener.onClick(position)
        }


    }

    private fun setSingleSelection(adapterPosition: Int) {
        if(adapterPosition == RecyclerView.NO_POSITION) return
        notifyItemChanged(singleItemSelectPosition)
        singleItemSelectPosition = adapterPosition
        notifyItemChanged(singleItemSelectPosition)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: HowOldAdapter.onClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvWeight: TextView = itemView.findViewById(R.id.tvWeight);


    }

    interface onClick {
        fun onClick(position: Int)
    }


}