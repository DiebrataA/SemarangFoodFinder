package com.anggarad.dev.foodfinder.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class RegisterModel(
    val success: Boolean? = null,
    val message: String? = null,
    val status: Int? = null,
    val userId: Int? = null,
    val accId: Int? = null
) : Parcelable
