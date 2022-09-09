package com.easylife.easylifes.trainerside.activities.clientdetail

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.easylife.easylifes.R
import com.easylife.easylifes.databinding.ActivityAllClientDetailBinding
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.trainerhome.TrainerUserDataModel
import com.easylife.easylifes.trainerside.adapter.AllClientsListAdapter
import com.easylife.easylifes.utils.Utilities

class AllClientDetailActivity : AppCompatActivity(),AllClientsListAdapter.onAllClientDetailClick {
    private lateinit var binding : ActivityAllClientDetailBinding
    private lateinit var utilties : Utilities
    private lateinit var allClientsList : ArrayList<TrainerUserDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllClientDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initViews()

    }
    private fun initViews() {
        utilties = Utilities(this@AllClientDetailActivity)
        utilties.setGrayBar(this@AllClientDetailActivity)

        allClientsList = ArrayList()
        allClientsList.add(TrainerUserDataModel(1,"Luke William","Luck william","","","","",""))
        allClientsList.add(TrainerUserDataModel(1,"Luke William","Luck william","","","","",""))
        allClientsList.add(TrainerUserDataModel(1,"Luke William","Luck william","","","","",""))
        allClientsList.add(TrainerUserDataModel(1,"Luke William","Luck william","","","","",""))
        allClientsList.add(TrainerUserDataModel(1,"Luke William","Luck william","","","","",""))
        allClientsList.add(TrainerUserDataModel(1,"Luke William","Luck william","","","","",""))
        binding.rvAllClients.layoutManager = LinearLayoutManager(this@AllClientDetailActivity,
            LinearLayoutManager.VERTICAL,false)
        binding.rvAllClients.adapter = AllClientsListAdapter(this@AllClientDetailActivity,allClientsList,this)    }

    override fun onClickArea(position: Int) {
        addWorkoutDialog()
    }

    private fun addWorkoutDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_workout)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes = lp
        val cardAddWorkout = dialog.findViewById<CardView>(R.id.cardAddWorkout)
        cardAddWorkout.setOnClickListener {
            creatWorkoutNow()
            dialog.dismiss()
        }

        dialog.show()
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
        val layout_send = dialog.findViewById<RelativeLayout>(R.id.layout_send)
        layout_send.setOnClickListener {
            startActivity(Intent(this@AllClientDetailActivity,WorkoutSelectionActivity::class.java))
        }

        dialog.show()
    }
}