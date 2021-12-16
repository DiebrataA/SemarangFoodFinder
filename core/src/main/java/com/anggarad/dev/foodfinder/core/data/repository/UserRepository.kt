package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.UserResponse
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val dataStoreManager: DataStoreManager,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IUserRepository {
    override suspend fun getUserDetail(userId: Int): Flow<ApiResponse<UserResponse>> {
        return remoteDataSource.getUserDetail(userId)
    }
//        object : NetworkBoundResource<UserDetail, UserResponse>() {
//            override fun loadFromDB(): Flow<UserDetail> {
//                return localDataSource.getUserData(userId).map {
//                    DataMapper.mapUserEntityToUser(it)
//                }
//            }
//
//            override fun shouldFetch(data: UserDetail?): Boolean {
//                return data == null
//            }
//
//            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
//                return remoteDataSource.getUserDetail(userId)
//            }
//
//            override suspend fun saveCallResult(data: UserResponse) {
//                val userDetail = DataMapper.mapUserResponseToEntity(data)
//                return localDataSource.insertUserData(userDetail)
//            }
//
//        }.asFlow()

    override fun getUserId(): Flow<Int> {
        return dataStoreManager.getUserId
    }
}