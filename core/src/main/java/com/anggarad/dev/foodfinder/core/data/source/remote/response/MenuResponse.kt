package com.anggarad.dev.foodfinder.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuResponse(

	@field:SerializedName("response")
	val response: List<MenuResponseItem>,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class MenuResponseItem(

    @field:SerializedName("resto_id")
	val restoId: Int,

    @field:SerializedName("menu_category")
	val menuCategory: Int,

    @field:SerializedName("menu_category_name")
	val menuCategoryName: String,

    @field:SerializedName("menu_id")
	val menuId: Int,

    @field:SerializedName("menu_name")
    val menuName: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("menu_price")
    val menuPrice: String,

    @field:SerializedName("is_recommended")
    val isRecommended: Int? = null,

    @field:SerializedName("menu_img")
    val menuImg: String? = null
) : Parcelable
