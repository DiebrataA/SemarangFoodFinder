package com.anggarad.dev.foodfinder.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "restos_menu")
data class MenuEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "menuId")
    var menuId: Int,
    @ColumnInfo(name = "restoId")
    var restoId: Int,
    @ColumnInfo(name = "menuCategory")
    var menuCategory: Int,
    @ColumnInfo(name = "menuCategoryName")
    var menuCategoryName: String,
    @ColumnInfo(name = "menuName")
    var menuName: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "menuPrice")
    var menuPrice: String,
    @ColumnInfo(name = "isRecommended")
    var isRecommended: Int? = null
)
