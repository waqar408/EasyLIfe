package com.easylife.easylifes.userside.activities.inbox

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityInboxBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.chat.inbox.MessageDataListModel
import com.easylife.easylifes.model.chat.inbox.MessagesResponseModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.userside.adapter.InboxAdapter
import com.easylife.easylifes.utils.OnLoadMoreListener
import com.easylife.easylifes.utils.RecyclerViewLoadMoreScroll
import com.easylife.easylifes.utils.Utilities
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.tabadol.tabadol.data.network.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class InboxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInboxBinding
    private var chatList: ArrayList<MessageDataListModel?> = ArrayList()
    private var emptyList: ArrayList<MessageDataListModel?> = ArrayList()

    private lateinit var utilities: Utilities
    private var myId = ""
    private var otherUserId = ""
    private var otherUserName = ""
    private var otherUserProfile = ""
    private var otherUserNicName = ""
    var page: Int = 1
    var limit: Int = 50
    var apiClient = ApiClient()
    private var options: PusherOptions? = null
    private var pusher: Pusher? = null
    private var imageUploaded: Boolean = false
    private var imagefile: File? = null
    private lateinit var imageFileToUpload: MultipartBody.Part
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var adapter: InboxAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInboxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
        setMessagePusher()


    }

    private fun onClicks() {
        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.sendMessage.setOnClickListener {
            val message = binding.edMessage.text.toString().trim()
            if (message == "") {
                utilities.showFailureToast(this@InboxActivity, "Please enter message to send...")
            } else {
                binding.edMessage.text.clear()
                senTextMessage(myId, otherUserId, message)
            }
        }
        binding.rlAttachment.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .start()
        }
    }


    private fun setMessagePusher() {

        // pusher
        options = PusherOptions().setCluster("ap1")
        pusher = Pusher("2836dd4e0740bd82c439", options)
        val channelId = "chat.$myId.messages"
        val channel = pusher!!.subscribe(channelId)
        channel.bind("messages") { event ->
            val gson = Gson()
            var data = event.data
            data = data.substring(1, data.length - 1)
            val message: MessageDataListModel = gson.fromJson(data, MessageDataListModel::class.java)
            val size = chatList.size
            chatList.add(message)
            runOnUiThread {
                // Stuff that updates the UI
                if (chatList.isNotEmpty()) {
                    binding.tvNoViewFound.visibility = View.GONE
                }
            }
            val item = chatList[chatList.size - 1]
            if (item!!.from_id.toInt() != myId.toInt()) {
                messageSeen(myId, otherUserId)
            }

            Handler(Looper.getMainLooper()).post {
               showAllMessagesRecycler()
//                setAdapter()
            }
        }
        pusher!!.connect()
    }

    private fun showAllMessagesRecycler() {
        /*binding.rvChat.layoutManager = LinearLayoutManager(this@InboxActivity,LinearLayoutManager.VERTICAL,false)
        binding.rvChat.adapter = InboxAdapter(this@InboxActivity, chatList)
        binding.rvChat.scrollToPosition(chatList.size - 1)
        binding.rvChat.isNestedScrollingEnabled = false*/
        adapter.addData(chatList)
        adapter.notifyDataSetChanged()


    }

    private fun messageSeen(myId: String, otherUserId: String) {
        val apiClient = ApiClient()
        val utils = Utilities(this@InboxActivity)
        val url = apiClient.BASE_URL + "read_messages/" + myId + "/" + otherUserId
        apiClient.getApiService().messageSeen(url)
            .enqueue(object : Callback<BaseResponse> {

                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    val signupResponse = response.body()
                    if (signupResponse!!.status) {
                        //
                    } else {

                        utils.showFailureToast(
                            this@InboxActivity,
                            signupResponse.message
                        )
                        Log.d("mess", signupResponse.message)

                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    utils.showFailureToast(this@InboxActivity, t.message!!)
                    Log.d("mess", t.message.toString())

                }


            })


    }

    private fun initViews() {
        utilities = Utilities(this@InboxActivity)
        utilities.setWhiteBars(this@InboxActivity)
        adapter = InboxAdapter(this@InboxActivity, emptyList)
        val intent = intent
        myId = intent.getStringExtra("myId").toString()
        otherUserId = intent.getStringExtra("otherUserId").toString()
        otherUserName = intent.getStringExtra("otherUserName").toString()
        otherUserNicName = intent.getStringExtra("otherUserNicName").toString()
        otherUserProfile = intent.getStringExtra("otherUserProfile").toString()
        if (otherUserName != "") {
            binding.tvOtherUserName.text = otherUserName
            binding.otherUserNicName.text = otherUserNicName
            Glide.with(this@InboxActivity).load(otherUserProfile).into(binding.imgProfile)
        }

        apiClient = ApiClient()
        getMessages()
        messageSeen(myId, otherUserId)
        setRVLayoutManager()
        setAdapter()
        setRVScrollListener()

//        chatList = ArrayList()
        page = 1
        limit = 50
        /*binding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                page++
                binding.idPBLoading.setVisibility(View.VISIBLE)
                getMessages()

            }
        })*/
    }

    private fun setAdapter() {

        adapter = InboxAdapter(this@InboxActivity,chatList)
        adapter.notifyDataSetChanged()
        binding.rvChat.adapter = adapter
        binding.rvChat.scrollToPosition(chatList.size-1)

    }
    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
        binding.rvChat.layoutManager = mLayoutManager
        binding.rvChat.setHasFixedSize(true)
        chatList.reverse()
    }

    private fun setRVScrollListener() {
        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                page++
                getMessages()
            }
        })
        binding.rvChat.addOnScrollListener(scrollListener)
    }

    private fun getMessages() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@InboxActivity)) {
            adapter.addLoadingView()
            val url = apiClient.BASE_URL + "get_messages/" + myId + "/" + otherUserId + "?page=" + page
            apiClient.getApiService().getMessages(url)
                .enqueue(object : Callback<MessagesResponseModel> {

                    override fun onResponse(
                        call: Call<MessagesResponseModel>,
                        response: Response<MessagesResponseModel>
                    ) {
                        val signupResponse = response.body()
                        if (signupResponse!!.status) {
                            if (!signupResponse.data.equals("")) {

                                chatList = signupResponse.data.data
                                limit = response.body()!!.data.last_page
                                if(page==1)
                                {
                                    emptyList = response.body()!!.data.data
                                }
//                                    emptyList.addAll(chatList)
                                Handler(Looper.myLooper()!!).postDelayed({
                                    adapter.removeLoadingView()
                                    if (emptyList.isEmpty()) {
                                        binding.tvNoViewFound.visibility = View.VISIBLE
                                    }
                                    adapter.addData(chatList)
                                    scrollListener.setLoaded()

                                    binding.rvChat.post {
                                        adapter.notifyDataSetChanged()
                                    }
                                }, 2000)


                            } else {
                                utilities.showFailureToast(
                                    this@InboxActivity,
                                    "No Data Found"
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@InboxActivity, signupResponse.message
                            )
                            Log.d("mess", signupResponse.message)

                        }
                    }

                    override fun onFailure(call: Call<MessagesResponseModel>, t: Throwable) {

                        utilities.showFailureToast(this@InboxActivity, t.message!!)
                        Log.d("mess", t.message.toString())

                    }


                })


        } else {

            utilities.showFailureToast(
                this@InboxActivity,
                resources.getString(R.string.checkinternet)
            )

        }

    }

    private fun senTextMessage(from_id: String, to_id: String, text: String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@InboxActivity)) {
            binding.sendMessage.visibility = View.GONE
            binding.rlAttachment.visibility = View.GONE
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().sendTextMessage(from_id, to_id, text, "text")
                .enqueue(object : Callback<BaseResponse> {

                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        val signupResponse = response.body()
                        binding.rlAttachment.visibility = View.VISIBLE
                        binding.sendMessage.visibility = View.VISIBLE
                        binding.dotloader.visibility = View.GONE

                        if (signupResponse!!.status) {
                            //nothing to show
                        } else {
                            utilities.showFailureToast(
                                this@InboxActivity,
                                signupResponse.message,
                            )
                        }


                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        binding.rlAttachment.visibility = View.VISIBLE
                        binding.sendMessage.visibility = View.VISIBLE
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@InboxActivity, t.message!!)
                    }


                })


        } else {

            utilities.showFailureToast(
                this@InboxActivity,
                resources.getString(R.string.checkinternet)
            )

        }
    }

    //imagepicker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            // binding.profileImage.setImageURI(uri)
            val photoFile = File(uri.path!!)
            imagefile = photoFile
            imageUploaded = true
            if (imageUploaded) {
                sendImageMessage()
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            utilities.showFailureToast(this@InboxActivity, ImagePicker.getError(data))

        } else {
            //do nothing here

        }
    }

    private fun sendImageMessage() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@InboxActivity)) {
            val mediatype = "image"
            val messagetype = "media"
            val myid: RequestBody = myId.toRequestBody("text/plain".toMediaTypeOrNull())
            val otheruserid: RequestBody =
                otherUserId.toRequestBody("text/plain".toMediaTypeOrNull())
            val mediaType: RequestBody =
                mediatype.toRequestBody("text/plain".toMediaTypeOrNull())
            val text: RequestBody =
                "Sent an attachment....".toRequestBody("text/plain".toMediaTypeOrNull())
            val messageType: RequestBody =
                messagetype.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestBody: RequestBody =
                RequestBody.create("*/*".toMediaTypeOrNull(), imagefile!!)
            imageFileToUpload =
                MultipartBody.Part.createFormData("media", imagefile!!.name, requestBody)
            binding.sendMessage.visibility = View.GONE
            binding.rlAttachment.visibility = View.GONE
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().sendImageMessage(
                myid,
                otheruserid,
                mediaType,
                text,
                messageType,
                imageFileToUpload
            )
                .enqueue(object : Callback<SignupResponseModel> {
                    override fun onResponse(
                        call: Call<SignupResponseModel>,
                        response: Response<SignupResponseModel>
                    ) {
                        binding.rlAttachment.visibility = View.VISIBLE
                        binding.sendMessage.visibility = View.VISIBLE
                        binding.dotloader.visibility = View.GONE
                        if (response.isSuccessful) {
                            //do nothing
                            Log.d("reponmsemss", response.body()!!.message)
                        } else {
                            val message: String = response.body()!!.message
                            Log.d("reponmsemss", response.body()!!.message)
                            utilities.showFailureToast(this@InboxActivity, message)

                        }
                    }

                    override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                        binding.rlAttachment.visibility = View.VISIBLE
                        binding.sendMessage.visibility = View.VISIBLE
                        binding.dotloader.visibility = View.GONE
                        Log.d("reponmsemss", t.message!!)
                        utilities.showFailureToast(this@InboxActivity, t.message.toString())

                    }
                })


        } else {

            utilities.showFailureToast(
                this@InboxActivity,
                resources.getString(R.string.checkinternet)
            )

        }
    }

}