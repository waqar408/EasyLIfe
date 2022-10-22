package com.easylife.easylifes.userside.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayurmitra.ayurmitra.model.reviews.ReviewDetailDataModel
import com.bumptech.glide.Glide
import com.easylife.easylifes.R
import com.google.android.material.imageview.ShapeableImageView
import java.util.*
import kotlin.collections.ArrayList


class ReviewsAdapter(
    val context: Context,
    val list: ArrayList<ReviewDetailDataModel>,
) :
    RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_reviews, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ReviewDetailDataModel = list[position]
        holder.tvName.text = model.username
        holder.review.text = model.review
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = model.review_time.toLong() * 1000L
        val date = DateFormat.format("dd-MM-yyyy HH:mm:ss", calendar).toString()
        holder.tvReviewDetail.text = date
        holder.ratingBar1.rating = model.rating_stars.toFloat()
        holder.tvRating.text = model.rating_stars
        Glide.with(context).load(model.profile_image).into(holder.imageHome)
       /* holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, ReviewListActivity::class.java))
        }*/

    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvReviewDetail: TextView = itemView.findViewById(R.id.tvReviewDetail)
        val review: TextView = itemView.findViewById(R.id.review)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        val imageHome: ShapeableImageView = itemView.findViewById(R.id.imgProfile)
        val ratingBar1 : RatingBar = itemView.findViewById(R.id.ratingBar1)

    }




}