package com.anggarad.dev.foodfinder.core.domain.model

data class MenuDetail(

    val restoId: Int,
    val menuCategory: Int,
    val menuCategoryName: String,
    val menuId: Int,
    val menuName: String,
    val description: String,
    val menuPrice: String,
    val isRecommended: Int? = null
)