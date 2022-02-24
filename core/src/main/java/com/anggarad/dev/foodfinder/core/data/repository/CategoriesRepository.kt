package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.NetworkBoundResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.CategoriesResponseItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RestoByCategoryItems
import com.anggarad.dev.foodfinder.core.domain.model.CategoriesDetail
import com.anggarad.dev.foodfinder.core.domain.model.RestoByCategoryDetail
import com.anggarad.dev.foodfinder.core.domain.repository.ICategoriesRepository
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoriesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ICategoriesRepository {
    override fun getRestoCategories(): Flow<Resource<List<CategoriesDetail>>> =
        object : NetworkBoundResource<List<CategoriesDetail>, List<CategoriesResponseItem>>() {
            override fun loadFromDB(): Flow<List<CategoriesDetail>> {
                return localDataSource.getRestoCategories().map {
                    DataMapper.mapCategoriesEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<CategoriesDetail>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<CategoriesResponseItem>>> {
                return remoteDataSource.getRestoCategories()
            }

            override suspend fun saveCallResult(data: List<CategoriesResponseItem>) {
                val listCategories = DataMapper.mapCategoriesResponseToEntity(data)
                return localDataSource.insertRestoCategories(listCategories)
            }

        }.asFlow()

    override fun getRestoByCategory(id: Int): Flow<Resource<List<RestoByCategoryDetail>>> =
        object : NetworkBoundResource<List<RestoByCategoryDetail>, List<RestoByCategoryItems>>() {
            override fun loadFromDB(): Flow<List<RestoByCategoryDetail>> {
                return localDataSource.getRestoByCategory(id).map {
                    DataMapper.mapRestoByCategoryEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<RestoByCategoryDetail>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<RestoByCategoryItems>>> {
                return remoteDataSource.getRestoByCategoryList(id)
            }

            override suspend fun saveCallResult(data: List<RestoByCategoryItems>) {
                val restoList = DataMapper.mapRestoByCategoryResponseToEntity(data)
                return localDataSource.insertRestoByCategory(restoList)
            }

        }.asFlow()


}