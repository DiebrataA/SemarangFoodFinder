package com.anggarad.dev.foodfinder.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "reviews_id")
    var reviewsId: Int? = null,
    @ColumnInfo(name = "resto_id")
    var restoId: Int? = null,
    @ColumnInfo(name = "date")
    var date: String? = null,
    @ColumnInfo(name = "comments")
    var comments: String? = null,
    @ColumnInfo(name = "user_id")
    var userId: Int? = null,
    @ColumnInfo(name = "rating")
    var rating: Int? = null,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "img_review_path")
    var imgReviewPath: String? = null,
    @ColumnInfo(name = "img_profile")
    var imgProfile: String? = null
)
