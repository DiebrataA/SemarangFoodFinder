package com.anggarad.dev.foodfinder.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class LoginResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("currUser")
    val currUser: CurrUserItem,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("token")
    val token: String
) : Parcelable

@Parcelize
data class CurrUserItem(

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("phone_num")
    val phoneNum: String,

    @field:SerializedName("acc_id")
    val accId: Int,

    @field:SerializedName("email")
    val email: String
) : Parcelable


