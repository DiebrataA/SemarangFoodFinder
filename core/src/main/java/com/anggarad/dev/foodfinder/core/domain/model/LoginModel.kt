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
    val address: String? = null,
    val userId: Int,
    val name: String,
    val phoneNum: String? = null,
    val accId: Int,
    val email: String,
    val imgProfile: String? = null
) : Parcelable


