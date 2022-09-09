package com.easylife.easylifes.userside.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityAchieveGoalBinding.inflate
import com.easylife.easylifes.model.chat.inbox.MessageDataListModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.utils.Constant
import com.easylife.easylifes.utils.Utilities
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import kotlinx.android.synthetic.main.progress_loading.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList


class InboxAdapter(
    val context: Context,
    private val listModel: ArrayList<MessageDataListModel?>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var utils: Utilities
    val apiClient = ApiClient()

    override fun getItemViewType(position: Int): Int {
        val model: MessageDataListModel? = listModel[position]
        return if (listModel[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else if (model?.message_type?.lowercase()?.contains("text")!!) {
            Constant.VIEW_TYPE_TEXT
        } else {
            Constant.VIEW_TYPE_IMAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        return if (viewType == Constant.VIEW_TYPE_TEXT) {
            view = layoutInflater.inflate(R.layout.item_chat_message, parent, false)
            ViewHolderTextMessage(view)
        } else if (viewType == Constant.VIEW_TYPE_IMAGE) {
            view = layoutInflater.inflate(R.layout.item_images_chat, parent, false)
            ViewHolderImagesMessage(view)
        } else {
            val view1 = layoutInflater.inflate(R.layout.progress_loading, parent, false)
            LoadingViewHolder(view1)
        }

    }

    fun addData(dataViews: ArrayList<MessageDataListModel?>) {
        this.listModel.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): MessageDataListModel? {
        return listModel[position]
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            listModel.add(null)
            notifyItemInserted(listModel.size - 1)
        }

    }

    fun removeLoadingView() {
        //Remove loading item
        if (listModel.size != 0) {
            listModel.removeAt(listModel.size - 1)
            notifyItemRemoved(listModel.size)
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_TEXT) {

            val model: MessageDataListModel? = listModel[position]


            utils = Utilities(context)
            val gsonn = Gson()
            val jsonn: String = utils.getString(context, "loginResponse")
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val useridd = java.lang.String.valueOf(obj.id)
            val fromId = model!!.id
            var datetime = ""
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = model.time.toLong() * 1000L
            val date = DateFormat.format("yyyy-MM-dd HH:mm:ss", calendar).toString()


            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = java.text.SimpleDateFormat(inputPattern)
            try {
                val date1 = inputFormat.parse(date)
                val p = PrettyTime()
                val millis = date1!!.time
                datetime = p.format(Date(millis))

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            if (model.message_type.lowercase().contains("text")) {

                val viewHolderPending: ViewHolderTextMessage = holder as ViewHolderTextMessage
                if (useridd.equals(model.from_id)) {
                    viewHolderPending.lnOtherChat.visibility = View.GONE
                    viewHolderPending.tvMyMessage.text = model.text
                    viewHolderPending.tvMyMessageTime.text = datetime
                } else {
                    viewHolderPending.lnMyChat.visibility = View.VISIBLE
                    viewHolderPending.tvOtherMessageTime.text = datetime
                    viewHolderPending.othersMessage.text = model.text
                    Glide.with(context).load(model.other_user.profile_image)
                        .into(viewHolderPending.userImage)
                }


            } else {
                val drawable = CircularProgressDrawable(context)
                drawable.setColorSchemeColors(R.color.appColor, R.color.appColor, R.color.appColor)
                drawable.centerRadius = 40f
                drawable.strokeWidth = 10f
                drawable.start()


                if (model.message_type.lowercase().contains("media")) {

                    if (model.media_type.lowercase().contains("image")) {
                        val viewHolderPending: ViewHolderImagesMessage =
                            holder as ViewHolderImagesMessage
                        if (useridd.equals(model.from_id)) {
                            viewHolderPending.lnOtherChat.visibility = View.GONE
                            Glide.with(context).load(model.media).placeholder(drawable)
                                .into(viewHolderPending.mySentImage)
                            viewHolderPending.tvMyMessageTime.text = datetime
                        } else {
                            viewHolderPending.lnMyChat.visibility = View.VISIBLE
                            viewHolderPending.tvOtherMessageTime.text = datetime
                            Glide.with(context).load(model.media).placeholder(drawable)
                                .into(viewHolderPending.otherUsersSentImage)
                            Glide.with(context).load(model.other_user.profile_image)
                                .into(viewHolderPending.userImage)
                        }
                    } else {
                        val viewHolderVideo: ViewHolderVideoMessage =
                            holder as ViewHolderVideoMessage
                        if (useridd.equals(model.from_id)) {
                            viewHolderVideo.lnOtherChat.visibility = View.GONE
                            Glide.with(context).load(model.media)
                                .into(viewHolderVideo.otherUsersSentVideo)
                            viewHolderVideo.tvMyMessageTime.text = datetime
                        } else {
                            viewHolderVideo.lnMyChat.visibility = View.VISIBLE
                            viewHolderVideo.tvOtherMessageTime.text = datetime
                            Glide.with(context).load(model.media).into(viewHolderVideo.mySentVideo)
                            Glide.with(context).load(model.other_user.profile_image)
                                .into(viewHolderVideo.userImage)
                        }

                    }
                }
            }
        }
        if (holder.itemViewType == Constant.VIEW_TYPE_IMAGE) {

            val model: MessageDataListModel? = listModel[position]


            utils = Utilities(context)
            val gsonn = Gson()
            val jsonn: String = utils.getString(context, "loginResponse")
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            val useridd = java.lang.String.valueOf(obj.id)
            val fromId = model!!.id
            var datetime = ""
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = model.time.toLong() * 1000L
            val date = DateFormat.format("yyyy-MM-dd HH:mm:ss", calendar).toString()


            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = java.text.SimpleDateFormat(inputPattern)
            try {
                val date1 = inputFormat.parse(date)
                val p = PrettyTime()
                val millis = date1!!.time
                datetime = p.format(Date(millis))

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            if (model.message_type.lowercase().contains("text")) {

                val viewHolderPending: ViewHolderTextMessage = holder as ViewHolderTextMessage
                if (useridd.equals(model.from_id)) {
                    viewHolderPending.lnOtherChat.visibility = View.GONE
                    viewHolderPending.tvMyMessage.text = model.text
                    viewHolderPending.tvMyMessageTime.text = datetime
                } else {
                    viewHolderPending.lnMyChat.visibility = View.VISIBLE
                    viewHolderPending.tvOtherMessageTime.text = datetime
                    viewHolderPending.othersMessage.text = model.text
                    Glide.with(context).load(model.other_user.profile_image)
                        .into(viewHolderPending.userImage)
                }


            } else {
                val drawable = CircularProgressDrawable(context)
                drawable.setColorSchemeColors(R.color.appColor, R.color.appColor, R.color.appColor)
                drawable.centerRadius = 40f
                drawable.strokeWidth = 10f
                drawable.start()

                if (model.message_type.lowercase().contains("media")) {

                    if (model.media_type.lowercase().contains("image")) {
                        val viewHolderPending: ViewHolderImagesMessage =
                            holder as ViewHolderImagesMessage
                        if (useridd.equals(model.from_id)) {
                            viewHolderPending.lnOtherChat.visibility = View.GONE
                            Glide.with(context).load(model.media).placeholder(drawable)
                                .into(viewHolderPending.mySentImage)
                            viewHolderPending.tvMyMessageTime.text = datetime
                        } else {
                            viewHolderPending.lnMyChat.visibility = View.VISIBLE
                            viewHolderPending.tvOtherMessageTime.text = datetime
                            Glide.with(context).load(model.media).placeholder(drawable)
                                .into(viewHolderPending.otherUsersSentImage)
                            Glide.with(context).load(model.other_user.profile_image)
                                .into(viewHolderPending.userImage)
                        }
                    }else{
                        val viewHolderPending: ViewHolderImagesMessage = holder as ViewHolderImagesMessage
                        viewHolderPending.itemView.visibility = View.GONE
                        val params = holder.itemView.layoutParams
                        params.height = 0
                        params.width = 0
                        holder.itemView.layoutParams = params
                    }
                    /*else {
                        val viewHolderVideo: ViewHolderVideoMessage =
                            holder as ViewHolderVideoMessage
                        if (useridd.equals(model.from_id)) {
                            viewHolderVideo.lnOtherChat.visibility = View.GONE
                            Glide.with(context).load(model.media)
                                .into(viewHolderVideo.otherUsersSentVideo)
                            viewHolderVideo.tvMyMessageTime.text = datetime
                        } else {
                            viewHolderVideo.lnMyChat.visibility = View.VISIBLE
                            viewHolderVideo.tvOtherMessageTime.text = datetime
                            Glide.with(context).load(model.media).into(viewHolderVideo.mySentVideo)
                            Glide.with(context).load(model.other_user.profile_image)
                                .into(viewHolderVideo.userImage)
                        }

                    }*/
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return listModel.size
    }

}


class ViewHolderTextMessage(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userImage: ShapeableImageView = itemView.findViewById(R.id.imgProfile)
    val lnOtherChat: LinearLayout = itemView.findViewById(R.id.lnOtherChat)
    val lnMyChat: LinearLayout = itemView.findViewById(R.id.lnMyChat)
    val othersMessage: TextView = itemView.findViewById(R.id.tvOthersMessage)
    val tvOtherMessageTime: TextView = itemView.findViewById(R.id.tvOtherMessageTime)
    val tvMyMessage: TextView = itemView.findViewById(R.id.tvMyMessage)
    val tvMyMessageTime: TextView = itemView.findViewById(R.id.tvMyMessageTime)
}

class ViewHolderImagesMessage(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val lnOtherChat: LinearLayout = itemView.findViewById(R.id.lnOtherChat)
    val lnMyChat: LinearLayout = itemView.findViewById(R.id.lnMyChat)
    val userImage: ShapeableImageView = itemView.findViewById(R.id.imgProfile)
    val otherUsersSentImage: ShapeableImageView = itemView.findViewById(R.id.trainerPic)
    val tvOtherMessageTime: TextView = itemView.findViewById(R.id.tvOtherMessageTime)
    val mySentImage: ShapeableImageView = itemView.findViewById(R.id.trainerPic2)
    val tvMyMessageTime: TextView = itemView.findViewById(R.id.tvMyMessageTime)
}

class ViewHolderVideoMessage(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val lnOtherChat: LinearLayout = itemView.findViewById(R.id.lnOtherChat)
    val lnMyChat: LinearLayout = itemView.findViewById(R.id.lnMyChat)
    val userImage: ShapeableImageView = itemView.findViewById(R.id.imgProfile)
    val otherUsersSentVideo: ShapeableImageView = itemView.findViewById(R.id.trainerPic)
    val tvOtherMessageTime: TextView = itemView.findViewById(R.id.tvOtherMessageTime)
    val mySentVideo: ShapeableImageView = itemView.findViewById(R.id.trainerPic)
    val tvMyMessageTime: TextView = itemView.findViewById(R.id.tvMyMessageTime)
}

class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
