package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRegister(
    var userId: Int? = 0,
    var accId: Int? = 0,
    var fullName: String? = null,
    var address: String? = "",
    var phoneNum: String? = "",
    var email: String? = null,
    var imgProfile: String? = ""
) : Parcelable