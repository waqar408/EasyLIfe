package com.easylife.easylifes.trainerside.activities.clientdetail

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityAllWorkoutsBinding
import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutDataListModel
import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutsResponseModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.trainerside.adapter.AllWorkoutsAdapter
import com.easylife.easylifes.utils.Utilities
import com.google.gson.Gson
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllWorkoutsActivity : AppCompatActivity(),AllWorkoutsAdapter.onAllWorkoutClick {
    private lateinit var binding: ActivityAllWorkoutsBinding
    private lateinit var utilities: Utilities
    private lateinit var allWorkoutCategoriesList: ArrayList<GetUserWorkoutDataListModel>
    private var myid = ""
    var clientId = ""
    private var workoutCategoryName = ""
    private var useridd = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllWorkoutsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        onClicks()
        getUserWorkouts()

    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            finish()
        }
        binding.rlAddWorkout.setOnClickListener {
                creatWorkoutNow()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@AllWorkoutsActivity)
        utilities.setGrayBar(this@AllWorkoutsActivity)
        val gsonn = Gson()
        val jsonn: String = utilities.getString(this@AllWorkoutsActivity, "loginResponse")
        val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
        myid = java.lang.String.valueOf(obj.id)
        useridd  = java.lang.String.valueOf(obj.id)

        val intent = intent
        clientId = intent.getStringExtra("clientid").toString()

    }

    private fun getUserWorkouts() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@AllWorkoutsActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().getUserWorkouts(useridd,clientId).enqueue(object : Callback<GetUserWorkoutsResponseModel> {
                    override fun onResponse(
                        call: Call<GetUserWorkoutsResponseModel>,
                        response: Response<GetUserWorkoutsResponseModel>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        val signupResponse = response.body()
                        if (signupResponse!!.status) {
                            //banner list data
                            //categories data
                            allWorkoutCategoriesList = ArrayList()
                            allWorkoutCategoriesList = response.body()!!.data.data
                            allClients(allWorkoutCategoriesList)
                        } else {
                            utilities.showFailureToast(this@AllWorkoutsActivity, signupResponse.message)
                        }
                    }
                    override fun onFailure(call: Call<GetUserWorkoutsResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                       // utilities.showFailureToast(this@AllWorkoutsActivity, t.message!!)
                    }
                })
        } else {
            utilities.showFailureToast(this@AllWorkoutsActivity, resources.getString(R.string.checkinternet))
        }
    }

    private fun allClients(categoriesList: ArrayList<GetUserWorkoutDataListModel>) {
        binding.rvAllWorkout.layoutManager = GridLayoutManager(this@AllWorkoutsActivity, 2)
        binding.rvAllWorkout.adapter = AllWorkoutsAdapter(this, categoriesList,this@AllWorkoutsActivity)

    }





    private fun creatWorkoutNow() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_create_workout)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes = lp
        val layoutsend = dialog.findViewById<RelativeLayout>(R.id.layout_send)
        val edWorkoutName = dialog.findViewById<EditText>(R.id.edWorkoutName)


        layoutsend.setOnClickListener {
            workoutCategoryName = edWorkoutName.text.toString()
            if (workoutCategoryName != "")
            {
                val intent = Intent(this@AllWorkoutsActivity,WorkoutSelectionActivity::class.java)
                intent.putExtra("clientid",clientId)
                intent.putExtra("categoryName",workoutCategoryName)
                intent.putExtra("from","workout")
                startActivity(intent)
                finish()
                dialog.dismiss()
            }else{
                utilities.showFailureToast(this@AllWorkoutsActivity,"Please Enter Workout Name")
            }

        }
        dialog.show()
    }

    override fun onClickArea(position: Int) {
       val model = allWorkoutCategoriesList[position]
        val intent =  Intent(this@AllWorkoutsActivity,UserWorkoutDetailActivity::class.java)
        intent.putExtra("from","from")
        intent.putExtra("clientid",clientId)
        intent.putExtra("categoryid",model.id.toString())
        intent.putExtra("categoryName",model.title)
        intent.putExtra("position",position.toString())
        startActivity(intent)
        finish()
    }




}