package com.anggarad.dev.foodfinder.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.domain.usecase.RestoUseCase

class HomeViewModel(restoUseCase: RestoUseCase) : ViewModel() {
    val getRestolist = restoUseCase.getRestoList().asLiveData()
    val getCafelist = restoUseCase.getCafeList().asLiveData()

}