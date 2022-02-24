package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoriesDetail(
    val categoryName: String,
    val categoryId: Int,
    val categoryImg: String? = null
) : Parcelable
