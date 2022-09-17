package com.easylife.easylifes.trainerside.activities.clientdetail

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.easylife.easylifes.databinding.ActivitySelectedWorkoutBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.model.categoryvideos.CategoryVideoDataModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.trainerside.adapter.SelectedWorkoutAdapter
import com.easylife.easylifes.utils.Utilities
import com.easylife.easylifes.utils.UtilityClass
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Response


class SelectedWorkoutActivity : AppCompatActivity(), SelectedWorkoutAdapter.onCategoryVideoClick {
    private lateinit var binding: ActivitySelectedWorkoutBinding
    private lateinit var utilities: Utilities
    private var allClientsList: ArrayList<AllWorkoutsDataListModel> = ArrayList()
    var tvWorkoutCategory = ""
    var clientid = ""
    var userId = ""
    var categoryid = ""
    var from = ""

    lateinit var adapter: SelectedWorkoutAdapter
    lateinit var apiClient: ApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
        onClick()
    }


    fun onClick() {

        binding.layoutBackArrow.setOnClickListener {
            val intent = Intent(this@SelectedWorkoutActivity, WorkoutSelectionActivity::class.java)
            intent.putExtra("clientid",clientid)
            intent.putExtra("workoutCategoryName",tvWorkoutCategory)
            intent.putExtra("categoryid",categoryid)
            intent.putExtra(from,"from")
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@SelectedWorkoutActivity, WorkoutSelectionActivity::class.java)
        intent.putExtra("clientid",clientid)
        intent.putExtra("workoutCategoryName",tvWorkoutCategory)
        intent.putExtra("categoryid",categoryid)
        intent.putExtra("from",from)
        startActivity(intent)
        finish()

    }


    private fun initViews() {
        utilities = Utilities(this@SelectedWorkoutActivity)
        utilities.setGrayBar(this@SelectedWorkoutActivity)
        apiClient = ApiClient()
        adapter = SelectedWorkoutAdapter(
            this@SelectedWorkoutActivity,
            allClientsList,
            this@SelectedWorkoutActivity
        )
        val intent = intent
        allClientsList = UtilityClass.instance!!.list
        tvWorkoutCategory = intent.getStringExtra("workoutCategoryName").toString()
        clientid = intent.getStringExtra("clientid").toString()
        categoryid = intent.getStringExtra("categoryid").toString()
        from = intent.getStringExtra("from").toString()
        binding.tvWorkoutCategory.text = tvWorkoutCategory
        Log.d("allclientlist", allClientsList.toString())

        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (!jsonn.isEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            userId = obj.id.toString()

        }


        setDataToRecyclerview()

        binding.layoutCreateWorkout.setOnClickListener {
            val intent = Intent(this@SelectedWorkoutActivity, WorkoutsListActivity::class.java)
            intent.putExtra("clientid",clientid)
//            intent.putParcelableArrayListExtra("list",allClientsList)
            intent.putExtra("workoutCategoryName",tvWorkoutCategory)
            intent.putExtra("categoryid",categoryid)
            intent.putExtra("from",from)
            startActivity(intent)
            finish()
        }
    }




    fun setDataToRecyclerview() {
        binding.rvSelectedWorkout.layoutManager =
            LinearLayoutManager(this@SelectedWorkoutActivity, LinearLayoutManager.VERTICAL, false)
        adapter = SelectedWorkoutAdapter(
            this@SelectedWorkoutActivity,
            allClientsList,
            this@SelectedWorkoutActivity
        )
        binding.rvSelectedWorkout.adapter = adapter
    }

    override fun onClickArea(position: Int, holder: SelectedWorkoutAdapter.ViewHolder) {
        val mode = allClientsList.get(position)

        allClientsList.removeAt(position)
        adapter.notifyItemRemoved(position)
        adapter.notifyDataSetChanged()
        if (allClientsList.size == 0) {
            binding.tvNoViewFound.visibility = View.VISIBLE
        }


    }

    /*fun createWorkout(
) {
    val jsonObject = JsonObject()
    jsonObject.addProperty("trainer_id", userId)
    jsonObject.addProperty("user_id", clientid)


    val array = JsonArray()
    if (allClientsList.size > 0) {
        for (i in 0 until allClientsList.size) {
            workoutid = allClientsList.get(i).id.toString()
            rep = allClientsList.get(i).reps.toString()
            res_minutes = allClientsList.get(i).reps_minutes.toString()
            reps_second = allClientsList.get(i).reps_seconds.toString()

            val obj = JsonObject()
            obj.addProperty("workout_id", workoutid.toInt())
            obj.addProperty("reps", rep.toInt())
            obj.addProperty("reps_minutes", res_minutes.toInt())
            obj.addProperty("reps_seconds", reps_second.toInt())

            array.add(obj)

        }
        jsonObject.add("workouts", array)

        binding.dotloader.visibility = View.VISIBLE
        apiClient.getApiService().createWorkout(jsonObject)
            .enqueue(object : retrofit2.Callback<BaseResponse?> {
                override fun onResponse(
                    call: Call<BaseResponse?>,
                    response: Response<BaseResponse?>
                ) {
                    if (response.body() != null) {
                        val status = response.body()!!.status
                        if (status == true) {
                            val intent = Intent(
                                this@SelectedWorkoutActivity,
                                AllWorkoutsActivity::class.java
                            )
                            intent.putExtra("clientid", clientid)
                            startActivity(intent)
                            finish()
                        } else {
                            utilities.showFailureToast(
                                this@SelectedWorkoutActivity,
                                response.body()!!.message
                            )
                        }
                    }

                }

                override fun onFailure(
                    call: Call<BaseResponse?>,
                    t: Throwable
                ) {
                    binding.dotloader.visibility = View.GONE
                    t.printStackTrace()
                    // showToast(t.message.toString())

                }
            })

    } else {

        Log.d("warning", "warningOutPut")

    }
}*/
}
