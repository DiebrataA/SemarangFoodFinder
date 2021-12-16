package com.anggarad.dev.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.domain.usecase.RestoUseCase

class FavoriteViewModel(restoUseCase: RestoUseCase) : ViewModel() {
    val favoriteResto = restoUseCase.getFavoriteResto().asLiveData()
}