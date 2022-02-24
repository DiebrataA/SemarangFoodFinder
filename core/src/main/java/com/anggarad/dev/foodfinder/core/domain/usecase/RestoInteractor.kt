package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.MenuDetail
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.model.SearchModel
import com.anggarad.dev.foodfinder.core.domain.repository.IRestoRepository
import kotlinx.coroutines.flow.Flow

class RestoInteractor(private val restoRepository: IRestoRepository) : RestoUseCase {
    override fun getRestoList(): Flow<Resource<List<RestoDetail>>> {
        return restoRepository.getRestoList()
    }

//    override fun getCafeList(): Flow<Resource<List<RestoDetail>>> {
//        return restoRepository.getCafeList()
//    }

    override fun getRestoDetailTest(restoId: Int): Flow<Resource<RestoDetail>> {
        return restoRepository.getRestoDetailTest(restoId)
    }

    override fun getRestoDetail(restoId: Int): Flow<RestoDetail> {
        return restoRepository.getRestoDetail(restoId)
    }


    override fun getFavoriteResto(): Flow<List<RestoDetail>> {
        return restoRepository.getFavoriteResto()
    }

    override fun setFavoriteResto(resto: RestoDetail, state: Boolean) {
        return restoRepository.setFavoriteResto(resto, state)
    }

    override fun getMenu(restoId: Int): Flow<Resource<List<MenuDetail>>> {
        return restoRepository.getMenu(restoId)
    }

    override suspend fun searchResto(key: String): Flow<Resource<List<SearchModel>>> {
        return restoRepository.searchResto(key)
    }
}