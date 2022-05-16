package com.anggarad.dev.foodfinder.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class SearchItemEntity(
    @ColumnInfo(name = "resto_id")
    var restoId: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "latitude")
    var latitude: String,
    @ColumnInfo(name = "longitude")
    var longitude: String,
    @ColumnInfo(name = "imgCover")
    var imgCover: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "search_Id")
    var searchId: Int = 0
}

