package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.NetworkBoundResource
import com.anggarad.dev.foodfinder.core.data.NetworkOnlyResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.MenuResponseItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.RestoItems
import com.anggarad.dev.foodfinder.core.data.source.remote.response.SearchItem
import com.anggarad.dev.foodfinder.core.data.source.remote.response.SingleRestoItem
import com.anggarad.dev.foodfinder.core.domain.model.MenuDetail
import com.anggarad.dev.foodfinder.core.domain.model.RestoDetail
import com.anggarad.dev.foodfinder.core.domain.model.SearchModel
import com.anggarad.dev.foodfinder.core.domain.repository.IRestoRepository
import com.anggarad.dev.foodfinder.core.utils.AppExecutors
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RestoRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IRestoRepository{
    override fun getRestoList(pullToRefresh: Boolean): Flow<Resource<List<RestoDetail>>> =
        object : NetworkBoundResource<List<RestoDetail>, List<RestoItems>>() {
            override fun loadFromDB(): Flow<List<RestoDetail>> {
                return localDataSource.getRestoListData().map {
                    DataMapper.mapRestoEntityToDomainList(it)
                }
            }

            override fun shouldFetch(data: List<RestoDetail>?): Boolean {
                return pullToRefresh || data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<RestoItems>>> {
                return remoteDataSource.getRestoList()
            }

            override suspend fun saveCallResult(data: List<RestoItems>) {
                val restoList = DataMapper.mapRestoResponsetoEntity(data)
                return localDataSource.insertResto(restoList)
            }

        }.asFlow()

    override fun getRestoDetail(restoId: Int): Flow<RestoDetail> {
        return localDataSource.getRestoDetail(restoId).map {
            DataMapper.mapRestoEntityToDomain(it)
        }
    }

    override fun getRestoDetailTest(restoId: Int): Flow<Resource<RestoDetail>> =
        object : NetworkBoundResource<RestoDetail, SingleRestoItem>() {
            override fun loadFromDB(): Flow<RestoDetail> {
                return localDataSource.getRestoDetail(restoId).map {
                    DataMapper.mapRestoEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: RestoDetail?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<ApiResponse<SingleRestoItem>> {
                return remoteDataSource.getRestoDetail(restoId)
            }

            override suspend fun saveCallResult(data: SingleRestoItem) {
                val restoItem = DataMapper.mapSingleRestoResponseToEntity(data)

                return localDataSource.insertSingleResto(restoItem)
            }

        }.asFlow()


    override fun getFavoriteResto(): Flow<List<RestoDetail>> {
        return localDataSource.getFavoriteResto().map {
            DataMapper.mapRestoEntityToDomainList(it)
        }
    }

    override fun getMenu(restoId: Int): Flow<Resource<List<MenuDetail>>> =
        object : NetworkBoundResource<List<MenuDetail>, List<MenuResponseItem>>() {
            override fun loadFromDB(): Flow<List<MenuDetail>> {
                return localDataSource.getMenuResto(restoId).map {
                    DataMapper.mapMenuEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MenuDetail>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MenuResponseItem>>> {
                return remoteDataSource.getRestoMenu(restoId)
            }

            override suspend fun saveCallResult(data: List<MenuResponseItem>) {
                val menuList = DataMapper.mapMenuResponseToEntity(data)
                return localDataSource.insertMenu(menuList)
            }

        }.asFlow()

    override fun setFavoriteResto(resto: RestoDetail, state: Boolean) {
        val restoEntity = DataMapper.mapRestoDomainToEntity(resto)
        appExecutors.diskIO().execute { localDataSource.setFavoriteResto(restoEntity, state) }
    }

    override suspend fun searchResto(key: String): Flow<Resource<List<SearchModel>>> =
        object : NetworkOnlyResource<List<SearchModel>, List<SearchItem>>() {
            override fun collectResult(data: List<SearchItem>): Flow<List<SearchModel>> {
                return flow { emit(DataMapper.mapSearchResponseToDomain(data)) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<SearchItem>>> {
                return remoteDataSource.searchResto(key)
            }
        }.asFlow()

    override suspend fun searchMenu(key: String): Flow<Resource<List<SearchModel>>> =
        object : NetworkOnlyResource<List<SearchModel>, List<SearchItem>>() {
            override fun collectResult(data: List<SearchItem>): Flow<List<SearchModel>> {

                return flow { emit(DataMapper.mapSearchResponseToDomain(data)) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<SearchItem>>> {
                return remoteDataSource.searchMenu(key)
            }
        }.asFlow()

}