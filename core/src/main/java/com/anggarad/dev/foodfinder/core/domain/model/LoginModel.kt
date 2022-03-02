package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginModel(
    val token: String,
    val currentUser: CurrentUserModel

) : Parcelable

@Parcelize
data class CurrentUserModel(
    val address: String,
    val userId: Int,
    val name: String,
    val phoneNum: String,
    val accId: Int,
    val email: String,
    val imgProfile: String? = null
) : Parcelable


