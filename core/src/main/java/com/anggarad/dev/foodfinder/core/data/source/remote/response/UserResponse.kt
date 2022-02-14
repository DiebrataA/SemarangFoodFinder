package com.anggarad.dev.foodfinder.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

    @field:SerializedName("response")
    val response: Response,

    @field:SerializedName("status")
    val status: Int
) : Parcelable

@Parcelize
data class Response(
    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("acc_id")
    val accId: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("phone_num")
    val phoneNum: String,

    @field:SerializedName("img_profile")
    val imgProfile: String? = null,

    @field:SerializedName("email")
    val email: String
) : Parcelable
