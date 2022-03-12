package com.anggarad.dev.foodfinder.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggarad.dev.foodfinder.core.BuildConfig
import com.anggarad.dev.foodfinder.core.R
import com.anggarad.dev.foodfinder.core.databinding.ItemLayoutReviewUserBinding
import com.anggarad.dev.foodfinder.core.domain.model.UserReviewDetails
import com.bumptech.glide.Glide

class UserReviewAdapter : RecyclerView.Adapter<UserReviewAdapter.ReviewViewHolder>() {

    private var listReview = ArrayList<UserReviewDetails>()

    companion object {
        const val SERVER_URL = BuildConfig.MY_SERVER_URL
    }


    fun setReviewList(newList: List<UserReviewDetails>?) {
        if (newList == null) return
        listReview.clear()
        listReview.addAll(newList)
        notifyDataSetChanged()
    }


    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding = ItemLayoutReviewUserBinding.bind(itemView)

        fun bind(itemReview: UserReviewDetails) {
            with(binding) {
                if (itemReview.imgReviewPath == null) {
                    reviewPhoto.visibility = View.GONE
                } else {
                    Glide.with(itemView.context)
                        .load("${itemReview.imgReviewPath}")
                        .placeholder(R.drawable.ic_image)
                        .into(reviewPhoto)
                }
                tvDate.text = itemReview.date
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

    override fun onBindViewHolder(holder: UserReviewAdapter.ReviewViewHolder, position: Int) {
        val data = listReview[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listReview.size
    }
}