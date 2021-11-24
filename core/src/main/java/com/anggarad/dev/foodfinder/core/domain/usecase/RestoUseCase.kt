package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import kotlinx.coroutines.flow.Flow

interface RestoUseCase {
    fun getRestoList(): Flow<Resource<List<RestoDetail>>>
//    fun getCafeList(): Flow<Resource<List<RestoDetail>>>

    //    suspend fun getRestoDetail() : Flow<Resource<RestoDetail>>
    fun getFavoriteResto(): Flow<List<RestoDetail>>
    suspend fun setFavoriteResto(resto: RestoDetail, state: Boolean)
}