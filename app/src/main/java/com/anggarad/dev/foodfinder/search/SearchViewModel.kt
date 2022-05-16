package com.anggarad.dev.foodfinder.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.SearchModel
import com.anggarad.dev.foodfinder.core.domain.usecase.RestoUseCase

class SearchViewModel(private val restoUseCase: RestoUseCase) : ViewModel() {

    suspend fun searchResto(key: String): LiveData<Resource<List<SearchModel>>> {
        return restoUseCase.searchResto(key).asLiveData()
    }

    suspend fun searchMenu(key: String): LiveData<Resource<List<SearchModel>>> {
        return restoUseCase.searchMenu(key).asLiveData()
    }

}