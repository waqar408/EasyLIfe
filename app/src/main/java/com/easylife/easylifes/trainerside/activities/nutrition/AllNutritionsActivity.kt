package com.easylife.easylifes.trainerside.activities.nutrition

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityAllNutritionsBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.trainerside.adapter.AllNutritionsAdapter
import com.easylife.easylifes.utils.Utilities

class AllNutritionsActivity : AppCompatActivity(),AllNutritionsAdapter.onAllClientDetailClick{
    private lateinit var binding: ActivityAllNutritionsBinding
    private lateinit var utilities: Utilities
    private lateinit var plansList: ArrayList<JobsDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllNutritionsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()
    }
    private fun initViews() {
        utilities = Utilities(this@AllNutritionsActivity)
        utilities.setGrayBar(this@AllNutritionsActivity)

        plansList = ArrayList()
        plansList.add(JobsDataModel(R.drawable.myplanicon, "Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon, "Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon, "Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon, "Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon, "Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon, "Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon, "Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon, "Nutrition"))
        plansList.add(JobsDataModel(R.drawable.myplanicon, "Workouts"))
        plansList.add(JobsDataModel(R.drawable.myplanicon, "Nutrition"))
        binding.rvAllNutritions.layoutManager = GridLayoutManager(this@AllNutritionsActivity, 2)
        binding.rvAllNutritions.adapter =
            AllNutritionsAdapter(this@AllNutritionsActivity, plansList, this)    }

    override fun onClickArea(position: Int) {
        addNutritionDialog()
    }

    private fun addNutritionDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_nutrition)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes = lp
        val cardAddWorkout = dialog.findViewById<CardView>(R.id.cardAddWorkout)
        cardAddWorkout.setOnClickListener {
            createNewNutrition()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun createNewNutrition() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_create_nutrition)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes = lp
        val layout_send = dialog.findViewById<RelativeLayout>(R.id.layout_send)
        layout_send.setOnClickListener {
            startActivity(
                Intent(
                    this@AllNutritionsActivity,
                    NutritionSelectionActivity::class.java
                )
            )
        }

        dialog.show()
    }
}