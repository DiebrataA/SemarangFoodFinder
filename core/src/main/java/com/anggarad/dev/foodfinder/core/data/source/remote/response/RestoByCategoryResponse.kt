package com.anggarad.dev.foodfinder.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RestoByCategoryResponse(

    @field:SerializedName("response")
    val response: List<RestoByCategoryItems>,

    @field:SerializedName("status")
    val status: Int? = null
) : Parcelable

@Parcelize
data class RestoByCategoryItems(

    @field:SerializedName("resto_id")
    val restoId: Int,

    @field:SerializedName("category_id")
    val categoryId: Int,

    @field:SerializedName("is_halal")
    val isHalal: Int,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("op_hours")
    val opHours: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("price_range")
    val priceRange: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("img_cover")
    val imgCover: String? = null,

    @field:SerializedName("contacts")
    val contacts: String,

    @field:SerializedName("rating_avg")
    val ratingAvg: Float? = null,

    @field:SerializedName("category_name")
    val categories: String,

    @field:SerializedName("have_toilet")
    val haveToilet: Int,

    @field:SerializedName("have_musholla")
    val haveMusholla: Int,

    @field:SerializedName("have_internet")
    val haveInternet: Int,

    @field:SerializedName("have_socket")
    val haveSocket: Int,

    @field:SerializedName("have_smoking_room")
    val haveSmokingRoom: Int,

    @field:SerializedName("have_meeting_room")
    val haveMeetingRoom: Int,

    @field:SerializedName("have_outdoor")
    val haveOutdoor: Int
) : Parcelable