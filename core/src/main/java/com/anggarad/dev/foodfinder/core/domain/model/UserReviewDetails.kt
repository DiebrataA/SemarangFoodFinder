package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserReviewDetails(
    val reviewsId: Int,
    val restoId: Int,
    val userId: Int,
    val comments: String? = null,
    val rating: Float,
    val date: String,
    val imgReviewPath: String? = null,
    val name: String,
) : Parcelable
