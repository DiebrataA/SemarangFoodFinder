package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.repository.IRestoRepository
import kotlinx.coroutines.flow.Flow

class RestoInteractor(private val restoRepository: IRestoRepository) : RestoUseCase {
    override fun getRestoList(): Flow<Resource<List<RestoDetail>>> {
        return restoRepository.getRestoList()
    }

//    override fun getCafeList(): Flow<Resource<List<RestoDetail>>> {
//        return restoRepository.getCafeList()
//    }

//    override suspend fun getRestoDetail(): Flow<Resource<RestoDetail>> {
//        return restoRepository.getRestoDetail()
//    }

    override fun getFavoriteResto(): Flow<List<RestoDetail>> {
        return restoRepository.getFavoriteResto()
    }

    override suspend fun setFavoriteResto(resto: RestoDetail, state: Boolean) {
        return restoRepository.setFavoriteResto(resto, state)
    }
}