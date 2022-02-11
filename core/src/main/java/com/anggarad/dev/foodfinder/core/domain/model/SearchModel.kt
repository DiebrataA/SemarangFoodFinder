package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchModel(
    val restoId: Int,
    val name: String,
    val location: String,
    val imgCover: String? = null,
) : Parcelable

