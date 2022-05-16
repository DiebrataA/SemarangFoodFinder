package com.anggarad.dev.foodfinder.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditProfileResponse(

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("response")
    val response: String,
) : Parcelable
