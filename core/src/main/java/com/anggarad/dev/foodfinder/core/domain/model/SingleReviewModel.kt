package com.anggarad.dev.foodfinder.core.domain.model

data class SingleReviewModel(
    val reviewsId: Int?,
    val restoId: Int?,
    val userId: Int?,
    val comments: String? = null,
    val rating: Int?,
    val date: String?,
    val imgReviewPath: String? = null,
    val name: String?,
    var isDeleted: Int? = null,
)
