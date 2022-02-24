package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.MenuDetail
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.model.SearchModel
import kotlinx.coroutines.flow.Flow

interface RestoUseCase {
    fun getRestoList(): Flow<Resource<List<RestoDetail>>>

    //    fun getCafeList(): Flow<Resource<List<RestoDetail>>>
    fun getRestoDetailTest(restoId: Int): Flow<Resource<RestoDetail>>
    fun getRestoDetail(restoId: Int): Flow<RestoDetail>

    fun getFavoriteResto(): Flow<List<RestoDetail>>
    fun setFavoriteResto(resto: RestoDetail, state: Boolean)

    fun getMenu(restoId: Int): Flow<Resource<List<MenuDetail>>>

    suspend fun searchResto(key: String): Flow<Resource<List<SearchModel>>>
}