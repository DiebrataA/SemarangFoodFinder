package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestoByCategoryDetail(
    val restoId: Int,
    val categoryId: Int,
    val name: String,
    val isHalal: Int,
    val opHours: String,
    val contacts: String,
    val address: String,
    val imgCover: String? = null,
    val priceRange: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean = false,
    val categories: String,
    val ratingAvg: Float? = null,
    val haveToilet: Int,
    val haveMusholla: Int,
    val haveInternet: Int,
    val haveSocket: Int,
    val haveSmokingRoom: Int,
    val haveMeetingRoom: Int,
    val haveOutdoor: Int,
    var distance: Float? = null,
) : Parcelable
