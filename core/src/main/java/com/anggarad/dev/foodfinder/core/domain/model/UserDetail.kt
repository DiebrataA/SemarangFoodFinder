package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetail (
    val userId : Int,
    val fullName : String,
    val address : String,
    val phoneNum : String,
    val email : String
        ) : Parcelable