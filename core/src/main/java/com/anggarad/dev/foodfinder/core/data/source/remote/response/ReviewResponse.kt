package com.anggarad.dev.foodfinder.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewResponse(

    @field:SerializedName("response")
    val response: List<ReviewItem>? = null,

    @field:SerializedName("status")
    val status: Int? = null
) : Parcelable

@Parcelize
data class ReviewItem(

    @field:SerializedName("resto_id")
    val restoId: Int? = null,

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("comments")
    val comments: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("reviews_id")
    val reviewsId: Int? = null,

    @field:SerializedName("rating")
    val rating: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("img_review_path")
    val imgReviewPath: String? = null,

    @field:SerializedName("img_profile")
    val imgProfile: String? = null


) : Parcelable
