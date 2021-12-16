package com.anggarad.dev.foodfinder.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDetailEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    val userId : Int,

    @ColumnInfo(name = "fullName")
    val fullName : String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "phoneNum")
    val phoneNum: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "acc_id")
    val accId: Int,

    @ColumnInfo(name = "img_profile")
    val imgProfile: String? = null
)