package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.MenuDetail
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.model.SearchModel
import kotlinx.coroutines.flow.Flow

interface IRestoRepository {
    fun getRestoList(pullToRefresh: Boolean): Flow<Resource<List<RestoDetail>>>

    //    fun getCafeList(): Flow<Resource<List<RestoDetail>>>
    fun getRestoDetail(restoId: Int): Flow<RestoDetail>
    fun getRestoDetailTest(restoId: Int): Flow<Resource<RestoDetail>>

    //    suspend fun getRestoDetail() : Flow<Resource<RestoDetail>>
    fun getFavoriteResto(): Flow<List<RestoDetail>>
    fun setFavoriteResto(resto: RestoDetail, state: Boolean)

    fun getMenu(restoId: Int): Flow<Resource<List<MenuDetail>>>
    suspend fun searchResto(key: String): Flow<Resource<List<SearchModel>>>
    suspend fun searchMenu(key: String): Flow<Resource<List<SearchModel>>>
}