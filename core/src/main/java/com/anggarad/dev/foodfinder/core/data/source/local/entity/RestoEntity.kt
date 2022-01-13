package com.anggarad.dev.foodfinder.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "restos")
data class RestoEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "restoId")
    var restoId: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "isHalal")
    var isHalal: Int,
    @ColumnInfo(name = "opHours")
    var opHours: String,
    @ColumnInfo(name = "contacts")
    var contacts: String,
    @ColumnInfo(name = "address")
    var address: String,
    @ColumnInfo(name = "imgCover")
    var imgCover: String? = null,
    @ColumnInfo(name = "imgMenuPath")
    var imgMenuPath: String? = null,
    @ColumnInfo(name = "priceRange")
    var priceRange: String,
    @ColumnInfo(name = "location")
    var location: String,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
    @ColumnInfo(name = "ratingAvg")
    var ratingAvg: Float? = null,
    @ColumnInfo(name = "categories")
    var categories: List<String>,
    @ColumnInfo(name = "have_toilet")
    var haveToilet: Int,
    @ColumnInfo(name = "have_musholla")
    var haveMusholla: Int,
    @ColumnInfo(name = "have_internet")
    var haveInternet: Int,
    @ColumnInfo(name = "have_socket")
    var haveSocket: Int,
    @ColumnInfo(name = "have_smoking_room")
    var haveSmokingRoom: Int,
    @ColumnInfo(name = "have_meeting_room")
    var haveMeetingRoom: Int,
    @ColumnInfo(name = "have_outdoor")
    var haveOutdoor: Int,
)