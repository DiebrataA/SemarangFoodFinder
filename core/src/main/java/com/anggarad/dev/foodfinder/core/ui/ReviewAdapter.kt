package com.anggarad.dev.foodfinder.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemLayoutReviewBinding
import com.anggarad.dev.foodfinder.core.domain.model.ReviewDetails
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var listReview = ArrayList<ReviewDetails>()
    fun setReviewList(newList: List<ReviewDetails>?) {
        if (newList == null) return
        listReview.clear()
        listReview.addAll(newList)
        notifyDataSetChanged()
    }


    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemLayoutReviewBinding.bind(itemView)
        private val calendar = Calendar.getInstance()
        private val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")
        fun bind(itemReview: ReviewDetails) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("http://192.168.1.5:4000/uploads/${itemReview.imgReviewPath}")
                    .into(reviewPhoto)
                Glide.with(itemView.context)
                    .load("http://192.168.1.5:4000/uploads/${itemReview.imgProfile}")
                    .into(avatarImage)
                val date = itemReview.date
                tvDate.text = simpleDateFormat.format(date)
                tvReviewComment.text = itemReview.comments
                nameUserReview.text = itemReview.name
                ratingReview.text = itemReview.rating.toString()
                Log.d("ReviewList: ", listReview.size.toString())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ReviewViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_layout_review, parent, false)
    )

    override fun onBindViewHolder(holder: ReviewAdapter.ReviewViewHolder, position: Int) {
        val data = listReview[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listReview.size
    }
}