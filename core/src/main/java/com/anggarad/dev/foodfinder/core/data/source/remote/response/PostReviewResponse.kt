package com.anggarad.dev.foodfinder.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostReviewResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("Success")
    val success: Boolean
) : Parcelable
