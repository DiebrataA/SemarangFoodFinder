package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestoDetail(
    val restoId : Int,
    val name: String,
    val isHalal: Int,
    val opHours: String,
    val contacts: String,
    val address: String,
    val imgCover: String? = null,
    val imgMenuPath: String? = null,
    val priceRange: String,
    val location: String,
    val isFavorite: Boolean,
    val ratingAvg: Float? = null,
    val categories: List<String>
): Parcelable
