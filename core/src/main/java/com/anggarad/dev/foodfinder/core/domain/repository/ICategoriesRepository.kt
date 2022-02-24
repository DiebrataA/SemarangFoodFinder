package com.anggarad.dev.foodfinder.core.domain.repository

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.CategoriesDetail
import com.anggarad.dev.foodfinder.core.domain.model.RestoByCategoryDetail
import kotlinx.coroutines.flow.Flow

interface ICategoriesRepository {
    fun getRestoCategories(): Flow<Resource<List<CategoriesDetail>>>
    fun getRestoByCategory(id: Int): Flow<Resource<List<RestoByCategoryDetail>>>
}