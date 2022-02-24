package com.anggarad.dev.foodfinder.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoriesEntity(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    var categoryId: Int,

    @ColumnInfo(name = "category_name")
    var categoryName: String,

    @ColumnInfo(name = "category_img")
    var categoryImg: String? = null
)
