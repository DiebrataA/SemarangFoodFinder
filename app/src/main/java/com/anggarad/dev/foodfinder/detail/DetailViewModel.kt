package com.anggarad.dev.foodfinder.detail

import androidx.lifecycle.ViewModel
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.usecase.RestoUseCase

class DetailViewModel(private val restoUseCase: RestoUseCase) : ViewModel() {

    suspend fun setFavoriteRestos(restoDetail: RestoDetail, newStatus: Boolean) {
        return restoUseCase.setFavoriteResto(restoDetail, newStatus)
    }
}