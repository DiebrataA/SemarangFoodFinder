package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.SearchItem
import com.anggarad.dev.foodfinder.core.domain.model.MenuDetail
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import kotlinx.coroutines.flow.Flow

interface RestoUseCase {
    fun getRestoList(): Flow<Resource<List<RestoDetail>>>
    fun getCafeList(): Flow<Resource<List<RestoDetail>>>
    fun getRestoDetail(restoId: Int): Flow<RestoDetail>

    fun getFavoriteResto(): Flow<List<RestoDetail>>
    fun setFavoriteResto(resto: RestoDetail, state: Boolean)

    fun getMenu(restoId: Int): Flow<Resource<List<MenuDetail>>>

    suspend fun searchResto(key: String): Flow<ApiResponse<List<SearchItem>>>
}