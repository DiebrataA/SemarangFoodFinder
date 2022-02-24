package com.anggarad.dev.foodfinder.restolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.RestoByCategoryDetail
import com.anggarad.dev.foodfinder.core.domain.usecase.CategoriesUseCase

class RestoByCategoryViewModel(private val categoriesUseCase: CategoriesUseCase) : ViewModel() {

    fun getRestoByCategory(restoId: Int): LiveData<Resource<List<RestoByCategoryDetail>>> {
        return categoriesUseCase.getRestoByCategory(restoId).asLiveData()
    }
}