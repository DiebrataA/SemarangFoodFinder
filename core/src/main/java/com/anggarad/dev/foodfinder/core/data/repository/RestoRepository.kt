package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.NetworkBoundResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RestoItems
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.repository.IRestoRepository
import com.anggarad.dev.foodfinder.core.utils.AppExecutors
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RestoRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IRestoRepository{
    override fun getRestoList(): Flow<Resource<List<RestoDetail>>> =
        object : NetworkBoundResource<List<RestoDetail>, List<RestoItems>>() {
            override fun loadFromDB(): Flow<List<RestoDetail>> {
                return localDataSource.getRestoListData().map {
                    DataMapper.mapRestoEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<RestoDetail>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<RestoItems>>> {
                return remoteDataSource.getRestoList()
            }

            override suspend fun saveCallResult(data: List<RestoItems>) {
                val restoList = DataMapper.mapRestoResponsetoEntity(data)
                return localDataSource.insertResto(restoList)
            }

        }.asFlow()

    override fun getCafeList(): Flow<Resource<List<RestoDetail>>> =
        object : NetworkBoundResource<List<RestoDetail>, List<RestoItems>>() {
            override fun loadFromDB(): Flow<List<RestoDetail>> {
                return localDataSource.getRestoListData().map {
                    DataMapper.mapRestoEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<RestoDetail>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<RestoItems>>> {
                return remoteDataSource.getCafeList()
            }

            override suspend fun saveCallResult(data: List<RestoItems>) {
                val restoList = DataMapper.mapRestoResponsetoEntity(data)
                return localDataSource.insertResto(restoList)
            }

        }.asFlow()


    override fun getFavoriteResto(): Flow<List<RestoDetail>> {
        return localDataSource.getFavoriteResto().map {
            DataMapper.mapRestoEntityToDomain(it)
        }
    }

    override suspend fun setFavoriteResto(resto: RestoDetail, state: Boolean) {
        val restoEntity = DataMapper.mapRestoDomainToEntity(resto)
        appExecutors.diskIO().execute { localDataSource.setFavoriteResto(restoEntity, state) }
    }
}