package com.anggarad.dev.foodfinder.core.domain.usecase

import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.domain.model.CategoriesDetail
import com.anggarad.dev.foodfinder.core.domain.model.RestoByCategoryDetail
import com.anggarad.dev.foodfinder.core.domain.repository.ICategoriesRepository
import kotlinx.coroutines.flow.Flow

class CategoriesInteractor(private val categoriesRepo: ICategoriesRepository) : CategoriesUseCase {
    override fun getRestoCategories(): Flow<Resource<List<CategoriesDetail>>> {
        return categoriesRepo.getRestoCategories()
    }

    override fun getRestoByCategory(id: Int): Flow<Resource<List<RestoByCategoryDetail>>> {
        return categoriesRepo.getRestoByCategory(id)
    }
}