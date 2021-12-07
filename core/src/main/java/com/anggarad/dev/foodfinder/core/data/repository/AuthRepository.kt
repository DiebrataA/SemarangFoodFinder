package com.anggarad.dev.foodfinder.core.data.repository

import com.anggarad.dev.foodfinder.core.data.DataStoreManager
import com.anggarad.dev.foodfinder.core.data.NetworkBoundResource
import com.anggarad.dev.foodfinder.core.data.Resource
import com.anggarad.dev.foodfinder.core.data.source.RemoteDataSource
import com.anggarad.dev.foodfinder.core.data.source.local.LocalDataSource
import com.anggarad.dev.foodfinder.core.data.source.remote.network.ApiResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.LoginResponse
import com.anggarad.dev.foodfinder.core.data.source.remote.response.UserResponse
import com.anggarad.dev.foodfinder.core.domain.model.UserDetail
import com.anggarad.dev.foodfinder.core.domain.repository.IUserRepository
import com.anggarad.dev.foodfinder.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val dataStoreManager: DataStoreManager,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IUserRepository {
    override suspend fun saveCredentials(token: String) {
        return dataStoreManager.saveToDataStore(token)
    }

    override fun getUserDetail(userId: Int): Flow<Resource<UserDetail>> =
        object : NetworkBoundResource<UserDetail, UserResponse>() {
            override fun loadFromDB(): Flow<UserDetail> {
                return localDataSource.getUserData(userId).map {
                    DataMapper.mapUserEntityToUser(it)
                }
            }

            override fun shouldFetch(data: UserDetail?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return remoteDataSource.getUserDetail(userId)
            }

            override suspend fun saveCallResult(data: UserResponse) {
                val userDetail = DataMapper.mapUserResponseToEntity(data)
                return localDataSource.insertUserData(userDetail)
            }

        }.asFlow()

    override suspend fun userLogin(
        email: String,
        password: String
    ): Flow<ApiResponse<LoginResponse>> {
        return remoteDataSource.userLogin(email, password)
    }

}