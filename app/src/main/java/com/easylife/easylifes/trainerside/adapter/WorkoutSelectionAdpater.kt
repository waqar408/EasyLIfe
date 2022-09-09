package com.easylife.easylifes.trainerside.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.easylife.easylifes.R
import com.easylife.easylifes.model.JobsDataModel
import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.model.categoryvideos.CategoryVideoDataModel
import com.easylife.easylifes.utils.Utilities
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView


class WorkoutSelectionAdpater(
    private val context: Context,
    employees: ArrayList<AllWorkoutsDataListModel>
) :
    RecyclerView.Adapter<WorkoutSelectionAdpater.MultiViewHolder?>() {
    private var employees: ArrayList<AllWorkoutsDataListModel>
    private var reps = ""
    private var minutes = ""
    private var seconds = ""

    private var reps1 = 0
    private var minutes1 = 0
    private var seconds1 = 0


    fun setEmployees(employees: ArrayList<AllWorkoutsDataListModel>) {
        this.employees = ArrayList<AllWorkoutsDataListModel>()
        this.employees = employees
        notifyDataSetChanged()
    }

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
        fun bind(employee: AllWorkoutsDataListModel) {
            val drawable = CircularProgressDrawable(context)
            drawable.setColorSchemeColors(R.color.appColor, R.color.appColor, R.color.appColor);
            drawable.setCenterRadius(25f);
            drawable.setStrokeWidth(6f);
            drawable.start();
            imgTick.visibility = if (employee.isChecked) View.VISIBLE else View.GONE
            textView.setText(employee.title)
            tvDescription.text = employee.description
            Glide.with(context).load(employee.media).placeholder(drawable).into(imgProfile)
            imgTick.visibility = if (!employee.isChecked) View.VISIBLE else View.GONE
            itemView.setOnClickListener(View.OnClickListener {
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


            })
        }

        init {
            textView = itemView.findViewById(R.id.tvName)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvDescription2 = itemView.findViewById(R.id.tvDescription2)
            imgProfile = itemView.findViewById(R.id.imgProfile)
            imgTick = itemView.findViewById(R.id.img)
            rl = itemView.findViewById(R.id.rl)

        }
    }

    private fun bottomsheetreps(position: Int,employee : AllWorkoutsDataListModel,
                                rl : RelativeLayout,imgTick : ImageView,
                                tvDescription : TextView,tvDescription2: TextView) {
        val utilities = Utilities(context)
        val bottomSheetDialog : BottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottom_reps)
        bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val canelBtn = bottomSheetDialog.findViewById<RelativeLayout>(R.id.layout_backArrow)
        val noOfReps = bottomSheetDialog.findViewById<EditText>(R.id.edNoOfReps)
        val noOfMinutes = bottomSheetDialog.findViewById<EditText>(R.id.edNoOfReps)
        val noOfSeconds = bottomSheetDialog.findViewById<EditText>(R.id.edNoOfReps)
        val btnDone = bottomSheetDialog.findViewById<TextView>(R.id.btnDone)


        btnDone!!.setOnClickListener{
            reps = noOfReps!!.text.toString()
            minutes = noOfMinutes!!.text.toString()
            seconds = noOfSeconds!!.text.toString()
            if (reps.equals(""))
            {
                Toast.makeText(context,"Please Enter Reps",Toast.LENGTH_SHORT).show()
            }else if (minutes.equals("")){
                Toast.makeText(context,"Please Enter Minutes",Toast.LENGTH_SHORT).show()
            }else if (seconds.equals(""))
            {
                Toast.makeText(context,"Please Enter Seconds",Toast.LENGTH_SHORT).show()
            }else{
                reps1= reps.toInt()
                minutes1 = minutes.toInt()
                seconds1 = seconds.toInt()
                tvDescription.text = "No of Reps: " +reps
                tvDescription2.text = minutes+" mins:"+seconds+" secs"

                employee.isChecked = !employee.isChecked
                if (employee.isChecked) {
                    rl.setBackgroundResource(R.drawable.selected_greenback)
                    imgTick.setColorFilter(ContextCompat.getColor(context, R.color.white))

                }
                if (!employee.isChecked) {
                    rl.setBackgroundResource(R.drawable.btn_outline_light_color)
                    imgTick.setColorFilter(ContextCompat.getColor(context, R.color.white))
                }
                bottomSheetDialog.dismiss()
                Log.d("positionss",position.toString())
            }
        }

        canelBtn!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    val all: ArrayList<AllWorkoutsDataListModel>
        get() = employees

    val selected: ArrayList<AllWorkoutsDataListModel>
        get() {
            val selected: ArrayList<AllWorkoutsDataListModel> = ArrayList<AllWorkoutsDataListModel>()
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