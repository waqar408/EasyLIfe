package com.easylife.easylifes.userside.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easylife.easylifes.R
import com.easylife.easylifes.model.trainerdetail.SubscriptionPackageDataModel
import com.easylife.easylifes.userside.activities.choosepackage.PaymentActivity


class PackageAdapter(
    val context: Context,
    val list: ArrayList<SubscriptionPackageDataModel>,
) :
    RecyclerView.Adapter<PackageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_choose_pacakge, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: SubscriptionPackageDataModel = list[position]
        holder.tvPacakgeName.text = model.package_name
        holder.tvPerido.text = " / "+model.validity_days +" Days"
        holder.tvPriceUnit.text = model.price_currency
        holder.tvPrice.text =  model.package_price

        holder.itemView.setOnClickListener {
            val intent = Intent(context,PaymentActivity::class.java)
            intent.putExtra("id",model.id.toString())
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvPacakgeName: TextView= itemView.findViewById(R.id.tvPacakgeName)
        val tvPriceUnit: TextView = itemView.findViewById(R.id.tvPriceUnit)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvPerido: TextView = itemView.findViewById(R.id.tvPerido)

    }




}