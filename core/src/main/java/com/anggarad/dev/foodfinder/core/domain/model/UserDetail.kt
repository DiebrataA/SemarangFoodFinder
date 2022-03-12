package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail (
    val userId: Int? = 0,
    val fullName: String? = "",
    val address: String? = "",
    val phoneNum: String? = "",
    val email: String? = "",
    val accId: Int? = 0,
    val imgProfile: String? = ""
) : Parcelable