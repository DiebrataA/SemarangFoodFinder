package com.anggarad.dev.foodfinder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.domain.usecase.MainUseCase

class MainViewModel(mainUseCase: MainUseCase) : ViewModel() {
    val checkToken = mainUseCase.checkToken().asLiveData()
}