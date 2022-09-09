package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.JobsDataModel


class CustomizeInterestAdapter(private val context: Context, employees: ArrayList<JobsDataModel>) :
    RecyclerView.Adapter<CustomizeInterestAdapter.MultiViewHolder?>() {
    private var employees: ArrayList<JobsDataModel>
    fun setEmployees(employees: ArrayList<JobsDataModel>) {
        this.employees = ArrayList<JobsDataModel>()
        this.employees = employees
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_customizeinterest, parent, false)
        return MultiViewHolder(view)
    }


    override fun onBindViewHolder(@NonNull multiViewHolder: CustomizeInterestAdapter.MultiViewHolder, position: Int) {
        multiViewHolder.bind(employees[position])
    }



    inner class MultiViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val textView: TextView
        private val imageView: ImageView
        private val shapeableimage : ImageView
        private val rl : RelativeLayout
        fun bind(employee: JobsDataModel) {
            imageView.visibility = if (employee.isChecked) View.VISIBLE else View.GONE
            textView.setText(employee.name)
            Glide.with(context).load(employee.image).into(imageView)
            imageView.visibility = if (!employee.isChecked) View.VISIBLE else View.GONE
            itemView.setOnClickListener(View.OnClickListener {
                employee.isChecked = !employee.isChecked
                if (employee.isChecked)
                {
                    rl.setBackgroundResource(R.drawable.selected_greenback)
                    imageView.setColorFilter(ContextCompat.getColor(context,R.color.white))
                }
                if (!employee.isChecked)
                {
                    rl.setBackgroundResource(R.drawable.btn_outline_light_color)
                    imageView.setColorFilter(ContextCompat.getColor(context,R.color.appColor))
                }

            })
        }

        init {
            textView = itemView.findViewById(R.id.name)
            imageView = itemView.findViewById(R.id.img)
            shapeableimage = itemView.findViewById(R.id.img)
            rl = itemView.findViewById(R.id.rl)
        }
    }

    val all: ArrayList<JobsDataModel>
        get() = employees

    val selected: ArrayList<JobsDataModel>
        get() {
            val selected: ArrayList<JobsDataModel> = ArrayList<JobsDataModel>()
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