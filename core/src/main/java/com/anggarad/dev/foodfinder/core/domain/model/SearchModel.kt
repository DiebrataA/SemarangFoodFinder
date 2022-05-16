package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchModel(
    val restoId: Int,
    val name: String,
    val latitude: Double,
    val isHalal: Int? = null,
    val longitude: Double,
    val imgCover: String? = null,
    val ratingAvg: Float? = null,
    val menu: List<MenuResto>? = null,
    var distance: Float? = null,
) : Parcelable

@Parcelize
data class MenuResto(
    val menuName: String? = null,
    val menuPrice: String? = null,
    val menuImg: String? = null,
    val isRecommended: Int? = null,
) : Parcelable

