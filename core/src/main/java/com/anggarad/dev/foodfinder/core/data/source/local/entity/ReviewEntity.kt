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
    val reviewsId: Int? = null,
    @ColumnInfo(name = "resto_id")
    val restoId: Int? = null,
    @ColumnInfo(name = "date")
    val date: String? = null,
    @ColumnInfo(name = "comments")
    val comments: String? = null,
    @ColumnInfo(name = "user_id")
    val userId: Int? = null,
    @ColumnInfo(name = "rating")
    val rating: Int? = null,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "img_review_path")
    val imgReviewPath: String? = null,
    @ColumnInfo(name = "img_profile")
    val imgProfile: String? = null
)
