package com.easylife.easylifes.trainerside.activities.clientdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityWorkoutSelectionBinding
import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.model.allworkouts.AllWorkoutsResponseModel
import com.easylife.easylifes.trainerside.adapter.WorkoutSelectionAdpater
import com.easylife.easylifes.utils.Utilities
import com.easylife.easylifes.utils.UtilityClass
import com.tabadol.tabadol.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class WorkoutSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutSelectionBinding
    private lateinit var utilities: Utilities
    private var categoryVideoList: ArrayList<AllWorkoutsDataListModel> = ArrayList()
    var filterList: ArrayList<AllWorkoutsDataListModel> = ArrayList()
    lateinit var adapter: WorkoutSelectionAdpater
    private var customizeList: ArrayList<AllWorkoutsDataListModel> = ArrayList()
    private var workoutCategoryId = ""
    private var workoutCategoryName = ""
    var clientid = ""
    private var categoryid = ""
    var from = ""
    var position = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        onClicks()
    }

    private fun onClicks() {
        binding.layoutBackArrow.setOnClickListener {
            if (from == "from")
            {
                val intent = Intent(this@WorkoutSelectionActivity,UserWorkoutDetailActivity::class.java)
                intent.putExtra("categoryid",categoryid)
                intent.putExtra("from",from)
                intent.putExtra("categoryName",workoutCategoryName)
                intent.putExtra("position",position)
                intent.putExtra("clientid",clientid)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@WorkoutSelectionActivity,AllWorkoutsActivity::class.java)
                intent.putExtra("categoryid",categoryid)
                intent.putExtra("from",from)
                intent.putExtra("clientid",clientid)
                startActivity(intent)
                finish()
            }

        }

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filterList.clear()
                if (s.toString().isEmpty()) {
                    binding.rvWorkoutSelection.adapter =
                        (WorkoutSelectionAdpater(this@WorkoutSelectionActivity, categoryVideoList))
                    adapter.notifyDataSetChanged()
                } else {
                    try {
                        filter(s.toString())
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(
                            this@WorkoutSelectionActivity,
                            "" + e.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        })

        binding.layoutAddToStack.setOnClickListener {
            UtilityClass.instance!!.list = ArrayList()
            adapter = WorkoutSelectionAdpater(this@WorkoutSelectionActivity, categoryVideoList)
            if (adapter.selected.size > 0) {
                for (i in 0 until adapter.selected.size) {
//                    customizeList.add(adapter.selected[i])
                    UtilityClass.instance!!.list.add(adapter.selected[i])

                }
                val intent = Intent(this@WorkoutSelectionActivity, SelectedWorkoutActivity::class.java)
                intent.putExtra("clientid",clientid)
                intent.putExtra("categoryName",workoutCategoryName)
                intent.putExtra("workoutCategoryId",workoutCategoryId)
                intent.putExtra("categoryid",categoryid)
                intent.putExtra("from",from)
                intent.putExtra("position",position)
                startActivity(intent)
                finish()

            } else {
                utilities.showFailureToast(
                    this@WorkoutSelectionActivity,
                    "Please select workout..."
                )
            }
            Log.d("codehere", customizeList.size.toString())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        categoryVideoList.clear()
        if (from == "from")
        {
            val intent = Intent(this@WorkoutSelectionActivity,UserWorkoutDetailActivity::class.java)
            intent.putExtra("categoryid",categoryid)
            intent.putExtra("from",from)
            intent.putExtra("categoryName",workoutCategoryName)
            intent.putExtra("position",position)
            intent.putExtra("clientid",clientid)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this@WorkoutSelectionActivity,AllWorkoutsActivity::class.java)
            intent.putExtra("categoryid",categoryid)
            intent.putExtra("from",from)
            intent.putExtra("clientid",clientid)
            startActivity(intent)
            finish()
        }
    }

    private fun filter(text: String) {
        if (categoryVideoList.size > 0) {
            for (post in categoryVideoList) {
                if (post.title.lowercase()
                        .contains(text.lowercase(Locale.getDefault()))
                ) {
                    filterList.add(post)
                }
            }
            binding.rvWorkoutSelection.adapter =
                (WorkoutSelectionAdpater(this@WorkoutSelectionActivity, filterList))
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(
                this@WorkoutSelectionActivity,
                "No User Found",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initViews() {
        utilities = Utilities(this@WorkoutSelectionActivity)
        utilities.setGrayBar(this@WorkoutSelectionActivity)
        adapter = WorkoutSelectionAdpater(this@WorkoutSelectionActivity,categoryVideoList)
        val intent = intent
        clientid = intent.getStringExtra("clientid").toString()
        workoutCategoryId = intent.getStringExtra("workoutCategoryId").toString()
        categoryid = intent.getStringExtra("categoryid").toString()
        workoutCategoryName = intent.getStringExtra("categoryName").toString()
        from = intent.getStringExtra("from").toString()
        position = intent.getStringExtra("position").toString()
        binding.workoutName.text = workoutCategoryName
        cagtegoryVideos()

    }



    private fun cagtegoryVideos() {
        val apiClient = ApiClient()
        if (utilities.isConnectingToInternet(this@WorkoutSelectionActivity)) {
            binding.dotloader.visibility = View.VISIBLE
            val url = apiClient.BASE_URL + "all-workouts"
            apiClient.getApiService().allWorkouts(url)
                .enqueue(object : Callback<AllWorkoutsResponseModel> {
                    override fun onResponse(
                        call: Call<AllWorkoutsResponseModel>,
                        response: Response<AllWorkoutsResponseModel>
                    ) {
                        val signupResponse = response.body()
                        binding.dotloader.visibility = View.GONE
                        if (signupResponse!!.status) {
                            //categories data
                            categoryVideoList = ArrayList()
                            categoryVideoList = response.body()!!.data.data
                            if (categoryVideoList.isEmpty()) {
                                binding.tvNoViewFound.visibility = View.VISIBLE
                            } else {
                                binding.tvNoViewFound.visibility = View.GONE
                            }
                            binding.rvWorkoutSelection.layoutManager = LinearLayoutManager(
                                this@WorkoutSelectionActivity,
                                LinearLayoutManager.VERTICAL, false
                            )
                            binding.rvWorkoutSelection.adapter =
                                WorkoutSelectionAdpater(
                                    this@WorkoutSelectionActivity,
                                    categoryVideoList
                                )
                        } else {
                            utilities.showFailureToast(
                                this@WorkoutSelectionActivity,
                                signupResponse.message
                            )
                        }
                    }

                    override fun onFailure(call: Call<AllWorkoutsResponseModel>, t: Throwable) {
                        binding.dotloader.visibility = View.GONE
                        utilities.showFailureToast(this@WorkoutSelectionActivity, t.message!!)
                    }
                })
        } else {
            utilities.showFailureToast(
                this@WorkoutSelectionActivity,
                resources.getString(R.string.checkinternet)
            )
        }

    }



}