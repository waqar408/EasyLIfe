package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.trainerhome.TrainerUserDataModel
import com.easylife.easylifes.userside.activities.inbox.InboxActivity
import com.easylife.easylifes.userside.activities.profile.UserProfileActivity
import com.easylife.easylifes.utils.Utilities
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson

class TrainerClientsAdapter(
    val context: Context,
    val list: ArrayList<TrainerUserDataModel>,
) :
    RecyclerView.Adapter<TrainerClientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_clients, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: TrainerUserDataModel = list.get(position)
        var userId = ""
        val utilities = Utilities(context)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(context, "loginResponse")
        if (!jsonn.isEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = obj.id.toString()
        }
        Glide.with(context).load(model.profile_image).into(holder.imgProfile)
        holder.tvName.text = model.name
        holder.tvUserName.text = model.username
        holder.rlChat.setOnClickListener {
            val intent = Intent(context, InboxActivity::class.java)
            intent.putExtra("myId",userId)
            intent.putExtra("otherUserId",model.id.toString())
            intent.putExtra("otherUserProfile",model.profile_image)
            intent.putExtra("otherUserName",model.name)
            intent.putExtra("otherUserNicName",model.username)
            context.startActivity(intent)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, UserProfileActivity::class.java)
            intent.putExtra("id",model.id.toString())
            context.startActivity(intent)
        }




    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imgProfile: ShapeableImageView = itemView.findViewById(R.id.imgProfile)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvUserName: TextView = itemView.findViewById(R.id.tvusername)
        val rlChat: RelativeLayout = itemView.findViewById(R.id.rlChat)

    }




}