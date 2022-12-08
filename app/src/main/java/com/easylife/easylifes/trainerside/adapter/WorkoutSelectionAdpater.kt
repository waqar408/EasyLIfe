package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.trainerside.activities.FullScreenVideoActivity
import com.google.android.material.imageview.ShapeableImageView


class WorkoutSelectionAdpater(
    private val context: Context,
    employees: ArrayList<AllWorkoutsDataListModel>
) :
    RecyclerView.Adapter<WorkoutSelectionAdpater.MultiViewHolder?>() {
    private var employees: ArrayList<AllWorkoutsDataListModel>
    /*private var reps = ""
    private var minutes = ""
    private var seconds = ""

    private var reps1 = 0
    private var minutes1 = 0
    private var seconds1 = 0*/


    /*fun setEmployees(employees: ArrayList<AllWorkoutsDataListModel>) {
        this.employees = ArrayList<AllWorkoutsDataListModel>()
        this.employees = employees
        notifyDataSetChanged()
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_workout_selection, parent, false)
        return MultiViewHolder(view)
    }


    override fun onBindViewHolder(
        @NonNull multiViewHolder: WorkoutSelectionAdpater.MultiViewHolder,
        position: Int
    ) {
        multiViewHolder.bind(employees[position])
    }


    inner class MultiViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val textView: TextView
        private val tvDescription: TextView
        private val tvDescription2: TextView
        private val imgTick: ImageView
        private val imgProfile: ShapeableImageView
        private val rl: RelativeLayout
        private val rlSelection : RelativeLayout
        private val lnVideo : LinearLayout
        fun bind(employee: AllWorkoutsDataListModel) {
            val drawable = CircularProgressDrawable(context)
            drawable.setColorSchemeColors(R.color.appColor, R.color.appColor, R.color.appColor)
            drawable.centerRadius = 25f
            drawable.strokeWidth = 6f
            drawable.start()
            imgTick.visibility = if (employee.isChecked) View.VISIBLE else View.GONE
            textView.text = employee.title
            tvDescription.text = employee.description
            Glide.with(context).load(employee.media).placeholder(drawable).into(imgProfile)
            imgProfile.setOnClickListener{
                val intent = Intent(context, FullScreenVideoActivity::class.java)
                intent.putExtra("videourl", employee.media)
                context.startActivity(intent)
            }
            lnVideo.setOnClickListener {
                val intent = Intent(context, FullScreenVideoActivity::class.java)
                intent.putExtra("videourl", employee.media)
                context.startActivity(intent)
            }
            imgTick.visibility = if (!employee.isChecked) View.VISIBLE else View.GONE
            rlSelection.setOnClickListener {
                employee.isChecked = !employee.isChecked
                if (employee.isChecked) {
                    rl.setBackgroundResource(R.drawable.selected_greenback)
                    imgTick.setColorFilter(ContextCompat.getColor(context, R.color.white))
                }
                if (!employee.isChecked) {
                    rl.setBackgroundResource(R.drawable.btn_outline_light_color)
                    imgTick.setColorFilter(ContextCompat.getColor(context, R.color.white))
                }
                //bottomsheetreps(position,employee,rl,imgTick,tvDescription,tvDescription2)


            }
        }

        init {
            textView = itemView.findViewById(R.id.tvName)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvDescription2 = itemView.findViewById(R.id.tvDescription2)
            imgProfile = itemView.findViewById(R.id.imgProfile)
            imgTick = itemView.findViewById(R.id.img)
            rl = itemView.findViewById(R.id.rl)
            rlSelection = itemView.findViewById(R.id.rlSelection)
            lnVideo =itemView.findViewById(R.id.lnVideo)

        }
    }


    val all: ArrayList<AllWorkoutsDataListModel>
        get() = employees

    val selected: ArrayList<AllWorkoutsDataListModel>
        get() {
            val selected: ArrayList<AllWorkoutsDataListModel> = ArrayList()
            for (i in employees.indices) {
                if (employees[i].isChecked) {

                    selected.add(employees[i])

                }
            }
            return selected
        }

    init {
        this.employees = employees
    }


    override fun getItemCount(): Int {
        return employees.size
    }


}