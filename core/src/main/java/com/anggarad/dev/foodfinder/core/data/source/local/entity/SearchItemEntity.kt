package com.anggarad.dev.foodfinder.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class SearchItemEntity(
    @ColumnInfo(name = "resto_id")
    val restoId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "imgCover")
    val imgCover: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "search_Id")
    var searchId: Int = 0
}

