package com.anggarad.dev.foodfinder.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse(

    @field:SerializedName("response")
    val response: List<SearchItem>,

    @field:SerializedName("status")
    val status: Int
) : Parcelable

@Parcelize
data class SearchItem(
    @field:SerializedName("resto_id")
    val restoId: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("location_lat")
    val latitude: Double,

    @field:SerializedName("is_halal")
    val isHalal: Int? = null,

    @field:SerializedName("location_lng")
    val longitude: Double,

    @field:SerializedName("img_cover")
    val imgCover: String? = null,

    @field:SerializedName("rating_avg")
    val ratingAvg: Float? = null,

    @field:SerializedName("menu")
    val menu: List<MenuItem>? = null,

    ) : Parcelable

@Parcelize
data class MenuItem(
    @field:SerializedName("menu_name")
    val menuName: String? = null,

    @field:SerializedName("menu_price")
    val menuPrice: String? = null,

    @field:SerializedName("menu_img")
    val menuImg: String? = null,

    @field:SerializedName("is_recommended")
    val isRecommended: Int? = null,

    ) : Parcelable
