package com.easylife.easylifes.trainerside.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityTrainerPortfolioBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.model.trainerportfolio.PortfolioDataModel
import com.easylife.easylifes.model.trainerportfolio.TrainerPortfolioResponseModel
import com.easylife.easylifes.trainerside.adapter.PortfolioAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class TrainerPortfolioActivity : AppCompatActivity(),PortfolioAdapter.onAllWorkoutClick,PortfolioAdapter.onDeleteClick {
    private lateinit var utilities: Utilities
    var userId = ""
    lateinit var portfolioList : ArrayList<PortfolioDataModel>
    private lateinit var binding : ActivityTrainerPortfolioBinding
    val REQUEST_VIDEO_CODE = 101
    lateinit var imageFileToUpload: MultipartBody.Part
    private var imageUploaded: Boolean = false
    var imagefile: File? = null
    var image = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainerPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClicks()
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.rlAdd.setOnClickListener {
            pickVideo()
        }

    }

    private fun initViews() {
        utilities = Utilities(this@TrainerPortfolioActivity)
        utilities.setGrayBar(this@TrainerPortfolioActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@TrainerPortfolioActivity, "loginResponse")
        if (!jsonn.isEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = obj.id.toString()

        }

        getPortfolio()
    }

    private fun getPortfolio() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@TrainerPortfolioActivity)) {
            val url = apiClient.BASE_URL + "trainer-portfolio/"+userId
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().trainerPortfolio(url)
                .enqueue(object : Callback<TrainerPortfolioResponseModel> {

                    override fun onResponse(
                        call: Call<TrainerPortfolioResponseModel>,
                        response: Response<TrainerPortfolioResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (signupResponse!!.status == true) {
                            if (!signupResponse.portfolioDataModel.equals("")) {

                                portfolioList = ArrayList()
                                portfolioList = signupResponse.portfolioDataModel
                                binding.rvPortfolio.layoutManager = GridLayoutManager(this@TrainerPortfolioActivity,2)
                                binding.rvPortfolio.adapter = PortfolioAdapter(this@TrainerPortfolioActivity,portfolioList
                                    ,this@TrainerPortfolioActivity,this@TrainerPortfolioActivity)


                            } else {
                                utilities.showFailureToast(
                                    this@TrainerPortfolioActivity,
                                    "No Data Found"
                                )
                            }
                        } else {
                            utilities.showFailureToast(
                                this@TrainerPortfolioActivity, signupResponse.message
                            )
                            Log.d("mess", signupResponse.message)

                        }
                    }

                    override fun onFailure(call: Call<TrainerPortfolioResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@TrainerPortfolioActivity, t.message!!)
                        Log.d("mess", t.message.toString())

                    }


                })


        } else {

            utilities.showFailureToast(
                this@TrainerPortfolioActivity,
                resources.getString(R.string.checkinternet)
            )

        }

    }

    override fun onClickArea(position: Int) {
        val model = portfolioList.get(position)
        val intent = Intent(this@TrainerPortfolioActivity, FullScreenVideoActivity::class.java)
        intent.putExtra("videourl", model.video)
        startActivity(intent)
    }
    private fun pickVideo() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_gallery)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes = lp
        val tv = dialog.findViewById<TextView>(R.id.tv)
        val layout_send = dialog.findViewById<RelativeLayout>(R.id.layout_send)
        tv.setOnClickListener {
            openGalleryForVideo()
            dialog.dismiss()
        }
        layout_send.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun openGalleryForVideo() {
        val i = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, REQUEST_VIDEO_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO_CODE) {
            if (resultCode == RESULT_OK) {
                when (requestCode) {
                    REQUEST_VIDEO_CODE -> {
                        val videoUri = getPath(data?.data!!)
                        val photoFile = File(videoUri!!.toUri().path!!)
                        imagefile = photoFile
                        imageUploaded = true
                        image = "video"
                        if (imageUploaded==true)
                        {
                            createPost()
                        }

                    }
                }
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
//                removeVideo(rowVideoView)
            }
        }
    }

    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        return if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } else null
    }

    fun createPost() {
        val apiClient    = ApiClient()
        val userId1: RequestBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBody: RequestBody = RequestBody.create("*/*".toMediaTypeOrNull(), imagefile!!)
        imageFileToUpload = MultipartBody.Part.createFormData("video", imagefile!!.name, requestBody)
        binding.dotloader.visibility = View.VISIBLE
        apiClient.getApiService().addTrainerVideo(
            userId1,
            imageFileToUpload
        )
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    binding.dotloader.visibility = View.GONE
                    if (response.isSuccessful) {
                        val status: Boolean = response.body()!!.status
                        if (status.equals(true)) {
                            val signupResponse = response.body()
                            val message: String = response.body()!!.message
                            utilities.showSuccessToast(this@TrainerPortfolioActivity,message)
                            getPortfolio()
                        } else {
                            val message: String = response.body()!!.message

                            utilities.showFailureToast(this@TrainerPortfolioActivity, message.toString())

                        }
                    } else {
                        val message: String = response.body()!!.message
                        utilities.showFailureToast(this@TrainerPortfolioActivity, message.toString())

                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    binding.dotloader.visibility = View.GONE
                    utilities.showFailureToast(this@TrainerPortfolioActivity, t.message.toString())

                }
            })


    }

    private fun bottomDeletePortfolio(portfolioId : String) {
        val bottomSheetDialog : BottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(this@TrainerPortfolioActivity)
        bottomSheetDialog.setContentView(R.layout.bottom_delete)
        bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val canelBtn = bottomSheetDialog.findViewById<RelativeLayout>(R.id.layout_backArrow)
        val btnDone = bottomSheetDialog.findViewById<TextView>(R.id.btnDone)
        val bottomName = bottomSheetDialog.findViewById<TextView>(R.id.bottomName)
        bottomName!!.text = "Delete Portfolio"
        btnDone!!.setOnClickListener{
            deletePortfolio(portfolioId)
            bottomSheetDialog.dismiss()
        }

        canelBtn!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    override fun onDeleteClick(position: Int) {
        val model = portfolioList.get(position)
        bottomDeletePortfolio(model.id.toString())
    }

    private fun deletePortfolio(portfolioId :String) {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@TrainerPortfolioActivity)) {
            val url = apiClient.BASE_URL + "delete-portfolio-video/"+portfolioId
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().deletePortfolio(url)
                .enqueue(object : Callback<BaseResponse> {

                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (signupResponse!!.status == true) {
                            utilities.showSuccessToast(this@TrainerPortfolioActivity,signupResponse.message)
                            getPortfolio()
                        } else {
                            utilities.showFailureToast(
                                this@TrainerPortfolioActivity, signupResponse.message
                            )
                            Log.d("mess", signupResponse.message)

                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@TrainerPortfolioActivity, t.message!!)
                        Log.d("mess", t.message.toString())

                    }


                })


        } else {

            utilities.showFailureToast(
                this@TrainerPortfolioActivity,
                resources.getString(R.string.checkinternet)
            )

        }

    }


}