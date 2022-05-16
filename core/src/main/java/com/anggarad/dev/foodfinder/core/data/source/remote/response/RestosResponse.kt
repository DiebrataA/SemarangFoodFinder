package com.anggarad.dev.foodfinder.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestosResponse(

    @field:SerializedName("response")
    val response: List<RestoItems>,

    @field:SerializedName("status")
    val status: Int,
) : Parcelable

@Parcelize
data class RestoItems(

    @field:SerializedName("resto_id")
    val restoId: Int,

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

    @field:SerializedName("location_lat")
    val latitude: Double,

    @field:SerializedName("location_lng")
    val longitude: Double,

    @field:SerializedName("img_cover")
    val imgCover: String? = null,

    @field:SerializedName("contacts")
    val contacts: String,

    @field:SerializedName("rating_avg")
    val ratingAvg: Float? = null,

    @field:SerializedName("category_name")
    val categories: List<String>? = null,

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
    val haveOutdoor: Int,
) : Parcelable
