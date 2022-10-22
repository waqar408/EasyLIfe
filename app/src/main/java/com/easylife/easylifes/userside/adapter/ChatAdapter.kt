package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.inbox.InboxActivity
import com.easylife.easylifes.model.chat.messenger.MessengerDataModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList


class ChatAdapter(
    val context: Context,
    val list: ArrayList<MessengerDataModel>,
) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_chats, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: MessengerDataModel = list[position]
        val drawable = CircularProgressDrawable(context)
        drawable.setColorSchemeColors(R.color.appColor, R.color.appColor, R.color.appColor)
        drawable.centerRadius = 25f
        drawable.strokeWidth = 6f
        drawable.start()
        Glide.with(context).load(model.other_user_avatar).placeholder(drawable).into(holder.otherUserImage)
        if(model.other_user_name == "")
        {
            holder.tvName.text = "Unknow User"
        }else{
            holder.tvName.text = model.other_user_name
        }
        holder.tvLastMessage.text = model.text
        val gsonn = Gson()
        val utilities = Utilities(context)
        val jsonn: String = utilities.getString(context, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        val userId: String = obj.id.toString()

        val datetime: String
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = model.time.toLong() * 1000L
        val date = DateFormat.format("yyyy-MM-dd HH:mm:ss", calendar).toString()
        val d =  java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(model.time.toLong())
        Log.d("mili", d)

//        holder.time.setText(time)
        if (model.unread_messages == 0)
        {
            holder.rlSeen.visibility  = View.GONE
        }else{
            holder.rlSeen.visibility = View.VISIBLE
            holder.tvSeen.text = model.unread_messages.toString()
        }
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val inputFormat = java.text.SimpleDateFormat(inputPattern)
        try {
            val date1 = inputFormat.parse(date)
            val p = PrettyTime()
            val millis = date1!!.time
            datetime = p.format(Date(millis))
            holder.tvMessageTime.text = datetime
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (model.unread_messages == 0)
        {
            holder.rlSeen.visibility  = View.GONE
        }else{
            holder.rlSeen.visibility = View.VISIBLE
            holder.tvSeen.text = model.unread_messages.toString()
        }

        holder.itemView.setOnClickListener {
            if (model.from_id == userId)
            {
                val intent = Intent(context,InboxActivity::class.java)
                intent.putExtra("myId", userId)
                intent.putExtra("otherUserId", model.to_id)
                intent.putExtra("otherUserProfile",model.other_user_avatar)
                intent.putExtra("otherUserName",model.other_user_name)
                intent.putExtra("otherUserNicName",model.other_user_name)
                context.startActivity(intent)
            }else{
                val myId = model.to_id
                val otherUserId = model.from_id
                val intent =Intent(context,InboxActivity::class.java)
                intent.putExtra("myId",myId)
                intent.putExtra("otherUserId",otherUserId)
                intent.putExtra("otherUserProfile",model.other_user_avatar)
                intent.putExtra("otherUserName",model.other_user_name)
                intent.putExtra("otherUserNicName",model.other_user_name)
                context.startActivity(intent)
            }
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvMessageTime: TextView = itemView.findViewById(R.id.tvMessageTime)
        val tvLastMessage: TextView = itemView.findViewById(R.id.tvLastMessage)
        val otherUserImage: ImageView = itemView.findViewById(R.id.imgProfile)
        val rlSeen: RelativeLayout = itemView.findViewById(R.id.rlSeen)
        val tvSeen: TextView = itemView.findViewById(R.id.tvSeen)

    }




}