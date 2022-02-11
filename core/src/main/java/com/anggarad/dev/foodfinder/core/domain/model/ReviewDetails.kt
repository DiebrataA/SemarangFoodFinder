package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewDetails(
    val restoId: Int? = null,
    val date: String? = null,
    val comments: String? = null,
    val userId: Int? = null,
    val reviewsId: Int? = null,
    val rating: Int? = null,
    val name: String? = null,
    val imgReviewPath: String? = null,
    val imgProfile: String? = null
) : Parcelable
