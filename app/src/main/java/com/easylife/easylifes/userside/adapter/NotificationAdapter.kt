package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.instructor.InstructorDetailActivity
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.notification.NotificationDataModel
import com.google.android.material.imageview.ShapeableImageView
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList


class NotificationAdapter(
    val context: Context,
    val list: ArrayList<NotificationDataModel>,
) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: NotificationDataModel = list[position]
//        Glide.with(context).load(model.notification_from_image).into(holder.imgProfile)
        holder.tvNotification.text = model.notification

        val datetime: String
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = model.notification_time.toLong() * 1000L
        val date = DateFormat.format("yyyy-MM-dd HH:mm:ss", calendar).toString()
        val d =  java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(model.notification_time.toLong())
        Log.d("mili", d)

        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val inputFormat = java.text.SimpleDateFormat(inputPattern)
        try {
            val date1 = inputFormat.parse(date)
            val p = PrettyTime()
            val millis = date1!!.time
            datetime = p.format(Date(millis))
            holder.tvTime.text = datetime
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, InstructorDetailActivity::class.java))
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvNotification: TextView = itemView.findViewById(R.id.tvNotification)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val imgProfile: ShapeableImageView = itemView.findViewById(R.id.imgProfile)

    }




}