package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail (
    val userId: Int,
    val fullName: String,
    val address: String,
    val phoneNum: String,
    val email: String,
    val accId: Int,
    val imgProfile: String? = null
) : Parcelable