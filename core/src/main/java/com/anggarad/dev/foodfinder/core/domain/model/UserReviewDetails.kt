package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserReviewDetails(
    val restoId: Int,
    val date: String,
    val comments: String? = null,
    val userId: Int,
    val reviewsId: Int,
    val rating: Int,
    val name: String,
    val imgReviewPath: String? = null,
) : Parcelable
