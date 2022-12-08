package com.easylife.easylifes.trainerside.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.model.allworkouts.WorkoutRepsAndRestModel
import com.easylife.easylifes.trainerside.activities.FullScreenVideoActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView


class SelectedWorkoutListAdapter(
    val context: Context,
    val list: ArrayList<AllWorkoutsDataListModel>,
    var mListener: onSelectedWorkoutClick

) :
    RecyclerView.Adapter<SelectedWorkoutListAdapter.ViewHolder>() {
    private var viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_selected_workout_list, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: AllWorkoutsDataListModel = list[position]
        holder.tvName.text = model.title
        Glide.with(context).load(model.media).into(holder.imgProfile)
        holder.tvDescription.text = model.description
        model.listReps = ArrayList()



        holder.layoutComplete.setOnClickListener {
            bottomsheetreps(position, model, holder.rvRepsAndRest,holder.tvName,holder.tvDescription,holder.imgProfile)

        }
        holder.lnVideo.setOnClickListener {
            val intent = Intent(context, FullScreenVideoActivity::class.java)
            intent.putExtra("videourl", model.media)
            context.startActivity(intent)
        }
        holder.imgProfile.setOnClickListener {
            val intent = Intent(context, FullScreenVideoActivity::class.java)
            intent.putExtra("videourl", model.media)
            context.startActivity(intent)
        }

    }


    private fun bottomsheetreps(
        position: Int,
        model: AllWorkoutsDataListModel,
        rv: RecyclerView,
        name : TextView,
        description : TextView,
        imgProfile : ShapeableImageView
    ) {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottom_reps)
        bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val canelBtn = bottomSheetDialog.findViewById<RelativeLayout>(R.id.layout_backArrow)
        val edNoOfReps = bottomSheetDialog.findViewById<EditText>(R.id.edNoOfReps)
        val edRepsMinutes = bottomSheetDialog.findViewById<EditText>(R.id.edRepsMinutes)
        val edRepsSeconds = bottomSheetDialog.findViewById<EditText>(R.id.edRepsSeconds)
        val edRestMinutes = bottomSheetDialog.findViewById<EditText>(R.id.edRestMinutes)
        val edRestSeconds = bottomSheetDialog.findViewById<EditText>(R.id.edRestSeconds)
        val btnDone = bottomSheetDialog.findViewById<TextView>(R.id.btnDone)
        var reps: String
        var repMinutes: String
        var repsSeconds: String
        var restMinute: String
        var restSeconds: String
        btnDone!!.setOnClickListener {
            reps = edNoOfReps!!.text.toString()
            restSeconds = edRestSeconds!!.text.toString()
            restMinute = edRestMinutes!!.text.toString()
            repMinutes = edRepsMinutes!!.text.toString()
            repsSeconds = edRepsSeconds!!.text.toString()
            if (reps == "") {
                Toast.makeText(context, "Please Enter Reps", Toast.LENGTH_SHORT).show()
            }  else if (restMinute == "") {
                Toast.makeText(context, "Please Enter Rest Minutes", Toast.LENGTH_SHORT).show()
            } else if (repsSeconds == "") {
                Toast.makeText(context, "Please Enter Rest Seconds", Toast.LENGTH_SHORT).show()
            } else {

                val item = WorkoutRepsAndRestModel(reps, repMinutes, repsSeconds, restMinute, restSeconds)
                var obj = model
                obj.listReps.add(item)
                val obj2 = AllWorkoutsDataListModel(obj.id,obj.workout_category_id,obj.media_type,obj.media,
                    obj.title,obj.description,obj.excercises,obj.calories,obj.time,obj.listReps)
                obj = obj2

                name.text = obj2.title
                Glide.with(context).load(obj2.media).into(imgProfile)
                description.text = obj2.description
                val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rv.layoutManager = layoutManager
                val adapter = RespAndRestAdapter(context, obj.listReps)
                rv.adapter = adapter
                rv.setRecycledViewPool(viewPool)
//                adapter.add(item)

                /*val list1 : ArrayList<WorkoutRepsAndRestModel> = ArrayList()
                list1.add(item)
                model.listReps?.add(item1)*/

                 bottomSheetDialog.dismiss()
                Log.d("positionss", position.toString())
            }
        }

        canelBtn!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View, listener: onSelectedWorkoutClick) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val imgProfile: ShapeableImageView = itemView.findViewById(R.id.imgProfile)
        val rvRepsAndRest: RecyclerView = itemView.findViewById(R.id.rvRepsAndRest)
        val layoutComplete: RelativeLayout = itemView.findViewById(R.id.layoutComplete)
        val lnVideo: LinearLayout = itemView.findViewById(R.id.lnVideo)

    }

    interface onSelectedWorkoutClick {
        fun onClickArea(position: Int)
    }


}