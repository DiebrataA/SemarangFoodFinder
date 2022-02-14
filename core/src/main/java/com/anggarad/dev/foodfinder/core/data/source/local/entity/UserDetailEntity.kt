package com.anggarad.dev.foodfinder.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDetailEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var userId: Int,

    @ColumnInfo(name = "name")
    var fullName: String,

    @ColumnInfo(name = "address")
    var address: String,

    @ColumnInfo(name = "phoneNum")
    var phoneNum: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "acc_id")
    var accId: Int,

    @ColumnInfo(name = "img_profile")
    var imgProfile: String? = null
)