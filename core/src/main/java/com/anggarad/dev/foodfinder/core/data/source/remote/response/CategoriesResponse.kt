package com.anggarad.dev.foodfinder.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoriesResponse(

    @field:SerializedName("response")
    val response: List<CategoriesResponseItem>,

    @field:SerializedName("status")
    val status: Int
) : Parcelable

@Parcelize
data class CategoriesResponseItem(

    @field:SerializedName("category_name")
    val categoryName: String,

    @field:SerializedName("category_id")
    val categoryId: Int,

    @field:SerializedName("category_img")
    val categoryImg: String? = null
) : Parcelable
