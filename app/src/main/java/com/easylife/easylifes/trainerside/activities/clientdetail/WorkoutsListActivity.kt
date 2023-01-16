package com.easylife.easylifes.trainerside.activities.clientdetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.databinding.ActivityWorkoutsListBinding
import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.model.signup.SignUpDataModel
import com.easylife.easylifes.trainerside.adapter.SelectedWorkoutListAdapter
import com.easylife.easylifes.utils.Utilities
import com.easylife.easylifes.utils.UtilityClass
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Response


class WorkoutsListActivity : AppCompatActivity(),
    SelectedWorkoutListAdapter.onSelectedWorkoutClick {
    private lateinit var binding: ActivityWorkoutsListBinding
    private lateinit var utilities: Utilities
    private var allWorkoutList: ArrayList<AllWorkoutsDataListModel> = ArrayList()
    private var tvWorkoutCategory = ""
    var clientid = ""
    var trainerid = ""
    var workoutid = ""
    val apiClient = ApiClient()
    var categoryid = ""
    var position = ""
    var from : String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        onClicks()

    }

    fun onClicks()
    {
        binding.layoutBackArrow.setOnClickListener {
            val intent = Intent(this@WorkoutsListActivity,SelectedWorkoutActivity::class.java)
            intent.putExtra("clientid",clientid)
            intent.putExtra("categoryName",tvWorkoutCategory)
            intent.putExtra("categoryid",categoryid)
            intent.putExtra("from",from)
            intent.putExtra("position",position)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@WorkoutsListActivity,SelectedWorkoutActivity::class.java)
        intent.putExtra("clientid",clientid)
        intent.putExtra("categoryName",tvWorkoutCategory)
        intent.putExtra("categoryid",categoryid)
        intent.putExtra("from",from)
        intent.putExtra("position",position)
        startActivity(intent)
        finish()

    }




    private fun initViews() {
        utilities = Utilities(this@WorkoutsListActivity)
        utilities.setGrayBar(this@WorkoutsListActivity)

        val intent = intent
//        allWorkoutList = intent.getParcelableArrayListExtra("list")!!
        allWorkoutList = UtilityClass.instance!!.list
        tvWorkoutCategory = intent.getStringExtra("categoryName").toString()
        clientid = intent.getStringExtra("clientid").toString()
        from = intent.getStringExtra("from").toString()
        categoryid = intent.getStringExtra("categoryid").toString()
        position = intent.getStringExtra("position").toString()
        binding.workoutCategoryName.text = tvWorkoutCategory

        val gsonn = Gson()
        val jsonn: String = utilities.getString(this, "loginResponse")
        if (jsonn.isNotEmpty()) {
            val obj: SignUpDataModel = gsonn.fromJson(jsonn, SignUpDataModel::class.java)
            trainerid = obj.id.toString()

        }

//        allWorkoutList.add(AllWorkoutsDataListModel(1,"","","","","","","","",listWorkoutRepsAndRest))
        binding.rvSelectedWorkoutList.layoutManager = LinearLayoutManager(this@WorkoutsListActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvSelectedWorkoutList.adapter = SelectedWorkoutListAdapter(this@WorkoutsListActivity, allWorkoutList, this)

        binding.layoutComplete.setOnClickListener {
            if (from.equals("from"))
            {
                addMoreWorkout()
            }else{
                createWorkout()
            }
            Log.d("listlist",allWorkoutList.toString())
        }
    }

    override fun onClickArea(position: Int) {
//        val model = listSelectedWorkoutList.get(position)
    }

    fun createWorkout() {
        val jsonObject = JsonObject()

        val array2 = JsonArray()
        var jsonObject2: JsonObject
        if (allWorkoutList.size > 0) {

            for (i in 0 until allWorkoutList.size) {

                val array1 = JsonArray()
                workoutid = allWorkoutList.get(i).id.toString()
                for (j in 0 until allWorkoutList.get(i).listReps.size)
                {
                    val rep = allWorkoutList.get(i).listReps.get(j).reps
                    val repsMinutes = allWorkoutList.get(i).listReps.get(j).reps_minutes
                    val repsSecond = allWorkoutList.get(i).listReps.get(j).reps_Second
                    val resminutes = allWorkoutList.get(i).listReps.get(j).rest_minutes
                    val restSeconds = allWorkoutList.get(i).listReps.get(j).reps_Second

                    val obj1 = JsonObject()
                    obj1.addProperty("reps", rep.toInt())
                    obj1.addProperty("reps_minutes", 0)
                    obj1.addProperty("reps_seconds",0)
                    obj1.addProperty("rest_minutes", resminutes.toInt())
                    obj1.addProperty("rest_seconds", restSeconds.toInt())
                    array1.add(obj1)

                }
                jsonObject2 = JsonObject()
                jsonObject2.addProperty("workout_id",workoutid)
                jsonObject2.add("data",array1)
                array2.add(jsonObject2)

            }
            jsonObject.addProperty("trainer_id", trainerid)
            jsonObject.addProperty("user_id", clientid)
            jsonObject.addProperty("workout_title",tvWorkoutCategory)
            jsonObject.add("workouts", array2)
            Log.d("jsonjsonjson",jsonObject.toString())
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().createWorkout(jsonObject)
                .enqueue(object : retrofit2.Callback<BaseResponse?> {
                    override fun onResponse(
                        call: Call<BaseResponse?>,
                        response: Response<BaseResponse?>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        if (response.body() != null) {
                            val status = response.body()!!.status
                            if (status) {
                                val intent = Intent(
                                    this@WorkoutsListActivity,
                                    AllWorkoutsActivity::class.java
                                )
                                intent.putExtra("clientid", clientid)
                                startActivity(intent)
                                finish()
                            } else {
                                utilities.showFailureToast(
                                    this@WorkoutsListActivity,
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
    }

    fun addMoreWorkout() {
        val jsonObject = JsonObject()
        val array2 = JsonArray()
        var jsonObject2: JsonObject
        if (allWorkoutList.size > 0) {
            for (i in 0 until allWorkoutList.size) {

                val array1 = JsonArray()
                workoutid = allWorkoutList.get(i).id.toString()
                for (j in 0 until allWorkoutList.get(i).listReps.size)
                {
                    val rep = allWorkoutList.get(i).listReps.get(j).reps
                    val repsMinutes = allWorkoutList.get(i).listReps.get(j).reps_minutes
                    val repsSeconfs = allWorkoutList.get(i).listReps.get(j).reps_Second
                    val res_minutes = allWorkoutList.get(i).listReps.get(j).rest_minutes
                    val rest_Seconds = allWorkoutList.get(i).listReps.get(j).reps_Second

                    val obj1 = JsonObject()
                    obj1.addProperty("reps", rep.toInt())
                    obj1.addProperty("reps_minutes", repsMinutes.toInt())
                    obj1.addProperty("reps_seconds", repsSeconfs.toInt())
                    obj1.addProperty("rest_minutes", res_minutes.toInt())
                    obj1.addProperty("rest_seconds", rest_Seconds.toInt())
                    array1.add(obj1)

                }
                jsonObject2 = JsonObject()
                jsonObject2.addProperty("workout_id",workoutid)
                jsonObject2.add("data",array1)
                array2.add(jsonObject2)

            }
            jsonObject.addProperty("user_workout_id", categoryid)
            jsonObject.add("workouts", array2)
            Log.d("jsonjsonjson",jsonObject.toString())
            binding.dotloader.visibility = View.VISIBLE
            apiClient.getApiService().addMoreVideo(jsonObject)
                .enqueue(object : retrofit2.Callback<BaseResponse?> {
                    override fun onResponse(
                        call: Call<BaseResponse?>,
                        response: Response<BaseResponse?>
                    ) {
                        binding.dotloader.visibility = View.GONE
                        if (response.body() != null) {
                            val status = response.body()!!.status
                            if (status) {
                                val intent = Intent(
                                    this@WorkoutsListActivity,
                                    AllWorkoutsActivity::class.java
                                )
                                intent.putExtra("clientid", clientid)
                                startActivity(intent)
                                finish()


                            } else {
                                utilities.showFailureToast(
                                    this@WorkoutsListActivity,
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
    }

}