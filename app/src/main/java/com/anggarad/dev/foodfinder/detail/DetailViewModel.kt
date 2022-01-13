package com.anggarad.dev.foodfinder.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.MenuDetail
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.usecase.RestoUseCase

class DetailViewModel(private val restoUseCase: RestoUseCase) : ViewModel() {


    fun getDetailResto(restoId: Int): LiveData<RestoDetail> {
        return restoUseCase.getRestoDetail(restoId).asLiveData()
    }


    fun setFavoriteRestos(restoDetail: RestoDetail, newState: Boolean) {

        return restoUseCase.setFavoriteResto(restoDetail, newState)
    }

    fun getMenu(restoId: Int): LiveData<Resource<List<MenuDetail>>> {
        return restoUseCase.getMenu(restoId).asLiveData()
    }
}