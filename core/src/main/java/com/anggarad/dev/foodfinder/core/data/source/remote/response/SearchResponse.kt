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

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("img_cover")
    val imgCover: String? = null,

    ) : Parcelable
