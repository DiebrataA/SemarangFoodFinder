package com.anggarad.dev.foodfinder.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.SearchItem
import com.anggarad.dev.foodfinder.core.domain.usecase.RestoUseCase

class SearchViewModel(private val restoUseCase: RestoUseCase) : ViewModel() {

    suspend fun searchResto(key: String): LiveData<ApiResponse<List<SearchItem>>> {
        return restoUseCase.searchResto(key).asLiveData()
    }

}